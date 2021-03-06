(function () {
  'use strict'
  angular
    .module('starter.controllers', [])


    /** @ngInject */
    .controller('MenuCtrl', function ($ionicSideMenuDelegate, $ionicPopup, RemoveImageService, $http, $scope, $ionicBackdrop, $rootScope, UserControlService, $ionicLoading, $ionicHistory, $timeout, AutoLoginService) {

      $scope.toggleLeft = function () {
        $ionicSideMenuDelegate.toggleLeft();
      };


      $scope.upload = function (flowFiles) {
        $scope.userMenu = $rootScope.user;
        $scope.usId = $scope.userMenu.id;
        $ionicLoading.show({
          template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color:white">Please wait...</p>'
        });
        if ($scope.userMenu.images.length > 0) {
          $scope.imgId = $scope.userMenu.images[0].id;
          $timeout(function () {
            RemoveImageService.delete({userid: $scope.usId, imageid: $scope.imgId}).$promise.then(function () {
            })
          }, 1000)
        }
        $timeout(function () {
          var UserId = $scope.userMenu.id;
          // set location
          flowFiles.opts.target = 'http://139.59.253.37:8080/userImage/add';
          flowFiles.opts.testChunks = false;
          flowFiles.opts.query = {UserId: UserId};
          flowFiles.upload();
          $timeout(function () {
            AutoLoginService.get({username: $scope.userMenu.username}
              , function (user1) {
                $timeout(function () {
                  $rootScope.user = user1;
                  $ionicLoading.hide();
                }, 2000)
              })
          }, 2500)
        }, 2500)

      }

      $scope.$on('flow::fileAdded', function (event, $flow, flowFile) {
        if ((flowFile.size / 1000) > 1024) {
          $ionicPopup.alert({
            title: 'Not Allow!',
            template: 'Your file size is too big<br>(not more than 1MB)'
          })
        } else {
          $scope.upload($flow)
        }
      })
      $scope.groups = [
        {
          name: 'Manage Account',
          items: [
            'Change Account information',
            'Change Password'
          ]
        },
        {
          name: 'History',
          items: [
            'Clock-In & Clock Out',
            'Calendar'
          ]
        }
      ]
    })


    /** @ngInject */
    .controller('LoginController', function ($scope, $location, $cookies, $ionicPopup, $rootScope, $ionicModal, $state, $ionicLoading, $timeout, $ionicHistory, UserService) {


      $scope.Login = function () {
        $ionicLoading.show({
          template: '<ion-spinner class="spinner-spiral"></ion-spinner><p style="color:white">Loading...</p>'
        });
        UserService.get(($scope.user)
          , function (user) {
            $rootScope.user = user;
            window.localStorage.setItem("Cookies", user.username);
            $timeout(function () {
              $ionicLoading.hide();
              $ionicHistory.clearHistory();
              $state.go('app.map')
            }, 4000)
          }, // unsuccess connection
          function (error) {
            $ionicLoading.hide();
            $ionicPopup.alert({
              title: 'Failed!',
              template: 'Username or Password is incorrect <br> or <br> No internet connection'
            }).then(function () {
              $timeout(function () {
                $ionicHistory.clearHistory();
                $ionicHistory.clearCache();
              }, 1500)
            })

          })
      }

      $scope.register = function () {
        $state.go('register', {}, {reload: true});
      };

    })


    /** @ngInject */
    .controller('MapCtrl', function ($ionicSideMenuDelegate, DailyCheckService, $scope, $rootScope, $ionicBackdrop, $stateParams, $state, $cordovaGeolocation, $http, $ionicPopup, locationService, $ionicLoading, $ionicHistory, $timeout) {

      $scope.$on('$ionicView.enter', function () {
        $ionicSideMenuDelegate.canDragContent(false);
      });
      $scope.$on('$ionicView.leave', function () {
        $ionicSideMenuDelegate.canDragContent(true);
      });

      var options = {timeout: 10000, enableHighAccuracy: true};

      var GeoCoder = new google.maps.Geocoder;
      $scope.prev_infowindow = false;

      window.CheckIn = function (name) {
        var confirmPopup = $ionicPopup.confirm({
          title: 'Please submit...',
          template: 'Are you sure you want to check in?'
        });

        confirmPopup.then(function (res) {
          if (res) {
            $ionicLoading.show({
              content: '<i class="icon ion-loading"></i>',
              template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Please wait...</p>'
            });
            $scope.usercheck = $rootScope.user;
            console.log($scope.usercheck)
            $scope.checkinData = {};
            $scope.checkinData.location = name;
            console.log($scope.checkinData)
            locationService.save({UserId: $scope.usercheck.id}, $scope.checkinData, function (data) {
              $ionicLoading.hide();
              $ionicPopup.alert({
                title: 'Result!',
                template: data.result
              }).then(function () {
                initialize();
              })
            }, function (error) {
              $ionicLoading.hide();
              $ionicPopup.alert({
                title: 'Failed!',
                template: 'Please check your internet connection'
              })
            })
          } else {
            console.log('You are not sure');
          }
        });
      }

      function createMarker(place) {
          var placeLoc = place.geometry.location;
          var infoWindow2 = new google.maps.InfoWindow();
          // var photos = place.photos;
          var marker2 = new google.maps.Marker({
            map: map,
            animation: google.maps.Animation.DROP,
            position: placeLoc
          });

          if($scope.checkResult=='Clock in'){
            google.maps.event.addListener(marker2, 'click', function () {
              infoWindow2.setContent('<div class="infoWindowS"><p style="font-size:18px;padding-top: 20px;"> Place name : ' + place.name + '</p>' +
                '<button class="button button-positive" style="margin:15px" ' +
                'onclick="CheckIn(\'' + place.name + '\')"> Clock in</button>'+ '</div>');
              if ($scope.prev_infowindow) {
                $scope.prev_infowindow.close()
              }
              $scope.prev_infowindow = infoWindow2;
              infoWindow2.open(map, this);
            });
          }else if($scope.checkResult=='Clock out'){
            google.maps.event.addListener(marker2, 'click', function () {
              infoWindow2.setContent('<div class="infoWindowS"><p style="font-size:18px;padding-top: 20px;"> Place name : ' + place.name + '</p>' +
                '<button class="button button-positive" style="margin:15px" ' +
                'onclick="CheckIn(\'' + place.name + '\')"> Clock out</button>'+ '</div>');
              if ($scope.prev_infowindow) {
                $scope.prev_infowindow.close()
              }
              $scope.prev_infowindow = infoWindow2;
              infoWindow2.open(map, this);
            });
          }else{
            google.maps.event.addListener(marker2, 'click', function () {
              infoWindow2.setContent('<div class="infoWindowS"><p style="font-size:18px;padding-top: 20px;"> Place name : ' + place.name + '</p>' +
                '<p>Can not check in(already Clock in & Clock out)</p>'+ '</div>');
              if ($scope.prev_infowindow) {
                $scope.prev_infowindow.close()
              }
              $scope.prev_infowindow = infoWindow2;
              infoWindow2.open(map, this);
            });
          }
      }

      function geocodePosition(pos) {

        GeoCoder.geocode({latLng: pos}, function (responses) {
          if (responses && responses.length > 0) {
            // alert(responses[0].geometry.location);
            var locationSearch = responses[0].geometry.location;
            var PlaceID = responses[0].place_id;
            var service = new google.maps.places.PlacesService(map);
            service.nearbySearch({
              location: locationSearch,
              radius: 200,
              type: ['accounting', 'airport', 'amusement_park', 'aquarium', 'art_gallery', 'atm', 'bakery', 'bank', 'bar', 'beauty_salon', 'bicycle_store', 'book_store', 'bowling_alley',
                'bus_station', 'cafe', 'campground', 'car_dealer', 'car_rental', 'car_repair', 'car_wash', 'casino', 'cemetery',
                'church', 'city_hall', 'clothing_store', 'convenience_store', 'courthouse', 'dentist', 'department_store', 'doctor', 'electrician', 'electronics_store', 'embassy', 'fire_station', 'florist', 'funeral_home', 'furniture_store', 'gas_station', 'grocery_or_supermarket', 'gym', 'hair_care', 'hardware_store', 'hindu_temple', 'home_goods_store', 'hospital', 'insurance_agency', 'jewelry_store', 'laundry', 'lawyer', 'library', 'liquor_store', 'local_government_office', 'locksmith', 'lodging', 'meal_delivery', 'meal_takeaway',
                'mosque', 'movie_rental', 'movie_theater', 'moving_company', 'museum', 'night_club', 'painter', 'park', 'parking', 'pet_store', 'pharmacy', 'physiotherapist', 'plumber', 'police', 'post_office', 'real_estate_agency', 'restaurant', 'roofing_contractor', 'rv_park', 'school', 'shoe_store', 'shopping_mall', 'spa', 'stadium', 'storage', 'store', 'subway_station', 'synagogue', 'taxi_stand', 'train_station', 'transit_station', 'travel_agency', 'university', 'veterinary_care', 'zoo'
              ]
            }, function (results, status) {
              if (status === google.maps.places.PlacesServiceStatus.OK) {
                for (var i = 0; i < results.length; i++) {
                  createMarker(results[i]);
                }
                console.log(results[1].name)
                $scope.Nearest = results[1].name;
              }
            })



            var icon = {
              url: "http://icons.iconarchive.com/icons/icons-land/vista-map-markers/256/Map-Marker-Flag-1-Right-Azure-icon.png",
              scaledSize: new google.maps.Size(50, 50), // scaled size
              origin: new google.maps.Point(0, 0), // origin
              anchor: new google.maps.Point(0, 0) // anchor
            };

            $scope.myLocationMark = new google.maps.Marker({
              position: pos,
              map: map,
              title: "My Location",
              animation: google.maps.Animation.DROP,
              icon: icon
            });


            service.getDetails({
              placeId: PlaceID
            }, function (place, status) {
              if (status === google.maps.places.PlacesServiceStatus.OK) {
                updateMarkerAddress(place.name);
              } else {
                updateMarkerAddress('Cannot determine address at this location.');
              }
            });
          }
        });

        var newMap = {
          center: pos,
          zoom: 18,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById("map"), newMap);

        $ionicLoading.show({
          template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Moving...</p><br><p style="color: white">Please Wait</p>',
          noBackdrop: false,
          animation: 'fade-in'
        });
        $timeout(function () {
          $ionicLoading.hide();
          $ionicHistory.nextViewOptions({
            disableBack: true,
            historyRoot: true
          });
          console.log("Finish");
          infoWindow.open(map, $scope.myLocationMark)
          $timeout(function () {
            infoWindow.close()
          }, 3000)
        }, 4500);


      }

      function updateMarkerAddress(Pname) {
        infoWindow.setContent("คุณอยู่ที่นี่.." + '<br>' + Pname);
      }

////////////////////////////////////////////////////////////////////////////////////


      var infoWindow = new google.maps.InfoWindow({
        maxWidth: 200
      });

      $scope.$on('$ionicView.beforeEnter',
        function (event, data) {
          if ($rootScope.user != null) {
            if (data.stateId == 'app.map') {
              initialize()
            }
          }
        })

      var map;

      $rootScope.call = function () {
        initialize();
      }

      function initialize() {
        DailyCheckService.get({UserId: $rootScope.user.id}, function (data) {
          console.log(data)
          $scope.checkResult = data.result;
        })
        var mapOptions = {
          center: new google.maps.LatLng(15.8700320, 100.9925410),
          zoom: 6,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        map = new google.maps.Map(document.getElementById("map"), mapOptions);
        $scope.map = map;


        $cordovaGeolocation.getCurrentPosition(options).then(function (position) {
            //Success
            var latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

            $ionicLoading.show({
              content: '<i class="icon ion-loading"></i>',
              template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Location Found!</p><br><p style="color: white">Please Wait</p>'
            });
            $timeout(function () {
              $ionicLoading.hide();
              $ionicHistory.nextViewOptions({
                disableBack: true,
                historyRoot: true
              });
              // Update current position info.
              geocodePosition(latLng);
            }, 6000);

            //Failed
          }, function (error) {
            $ionicLoading.show({
              template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Location Not found!</p><br><p style="color: white">Please check you service again</p>'
            });
            console.log(error);
            $timeout(function () {
              $ionicLoading.hide()
            }, 4000)
          }
        );
      }


    })


    /** @ngInject */
    .controller('registerController', function ($ionicPlatform, $cordovaDevice, $timeout, $ionicLoading, DepartmentService, $scope, $rootScope, $window, UserControlService, $ionicPopup, $state, $ionicHistory, verifyEmailService) {

      $scope.verifyEmailResult = false;

      $scope.form = document.getElementById("registerform");
      $scope.pic = false;

      $scope.$on('$ionicView.enter', function () {
        $ionicLoading.show({
          content: '<i class="icon ion-loading"></i>',
          template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Loading..</p>'
        });
        $timeout(function () {
          $ionicLoading.hide()
        }, 2000)
      });

      $scope.queryPromise = DepartmentService.query(function (data) {
        $scope.rolelist = data;
        $scope.user = {department: 'Chairman'}
      })


      $scope.sendRegister = function (flowFiles) {
        if ($scope.uuid != undefined) {
          $scope.user.uuid = $scope.uuid;
        }
        console.log('UUID is ... ' + $scope.user.uuid)
        UserControlService.save($scope.user, function (data) {
          var UserId = data.id;
          // set location
          $ionicLoading.show({
            content: '<i class="icon ion-loading"></i>',
            template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Loading...</p>'
          });
          if (flowFiles.files.length != 0) {
            $timeout(function () {
              flowFiles.opts.target = 'http://139.59.253.37:8080/userimage/add';
              flowFiles.opts.testChunks = false;
              flowFiles.opts.query = {UserId: UserId};
              flowFiles.upload();
            }, 3000)

          }

          $timeout(function () {
            $ionicLoading.hide();
            $ionicPopup.alert({
              title: 'Registration successful!',
              template: 'You have successfully registered for Time Attendance'
            }).then(function () {
              $ionicHistory.nextViewOptions({
                disableBack: true,
                historyRoot: true
              });
              $ionicHistory.clearCache().then(function () {
                $state.go('login')
              });
            }, function (error) {
              $ionicLoading.hide();
              $ionicPopup.alert({
                title: 'Failed!',
                template: 'Please check your internet connection'
              })
            })
          }, 3500)

        })

      }

      $scope.resetForm = function () {
        $ionicHistory.clearCache().then(function () {
          $state.go($state.current, {}, {reload: true})
        })
      }

      // Triggered in the login modal to close it
      $scope.closeRegister = function () {
        $ionicHistory.clearCache().then(function () {
          $ionicHistory.goBack();
        })
      };

      $scope.resetVerify = function () {
        $scope.verifyEmailResult = false;
      }

      $scope.verifyEmail = function (Email) {
        console.log(Email)
        verifyEmailService.get({username: $scope.user.username}, function (result) {
          console.log(result)
          $scope.verifyEmailResult = true;
          alert("You can use this email")
        }, function (error) {
          $scope.verifyEmailResult = false;
          console.log("Error******")
          console.log(error)
          alert("Email is already existed")
        })
      }

    })


    /** @ngInject */
    .directive('wjValidationError', function () {
      return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctl) {
          scope.$watch(attrs['wjValidationError'], function (errorMsg) {
            elm[0].setCustomValidity(errorMsg);
            ctl.$setValidity('wjValidationError', errorMsg ? false : true);
          });
        }
      };
    })


    /** @ngInject */
    .controller('editAccountController', function (DepartmentService, $ionicHistory, RemoveImageService, $timeout, UserService, $scope, $rootScope, $ionicPopup, UserControlService, $ionicLoading) {


      $scope.usernew = {}
      $scope.Check = null;

      // $scope.usernew.email = $rootScope.user.email;
      // $scope.usernew.tel = $rootScope.user.tel;
      // $scope.usernew.department = $rootScope.user.department;
      // $scope.usernew.name = $rootScope.user.name;
      $scope.usernew = $rootScope.user


      $scope.$on('$ionicView.enter', function () {
        $ionicLoading.show({
          content: '<i class="icon ion-loading"></i>',
          template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Loading..</p>'
        });
        $scope.queryPromise = DepartmentService.query(function (data) {
          $scope.rolelist = data;
        })
        $timeout(function () {
          $ionicLoading.hide()
        }, 2000)
      });


      $scope.updateInfo = function (flowFiles) {
        if ($rootScope.user != null) {
          $scope.usernew = {
            id: $rootScope.user.id,
            name: $scope.usernew.name,
            username: $rootScope.user.username,
            email: $rootScope.user.email,
            password: $rootScope.user.password,
            roles: $rootScope.user.roles,
            tel: $scope.usernew.tel,
            department: $scope.usernew.department,
            images: $rootScope.user.images
          }
          console.log($scope.usernew);
          $ionicLoading.show({
            template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color:white">Please wait...</p>'
          });
          if ($scope.Check == $rootScope.user.password) {
            UserControlService.update({id: $scope.usernew.id}, $scope.usernew, function (user2) {
              console.log(user2)
              var UserId = user2.id;
              // set location
              $ionicHistory.clearHistory();
              $ionicHistory.clearCache();
              $ionicHistory.nextViewOptions({
                disableBack: true,
                historyRoot: true
              })
              $timeout(function () {
                $timeout(function () {
                  $ionicLoading.hide();
                  $ionicPopup.alert({
                    title: 'Success',
                    template: 'Reloading...'
                  }).then(function () {
                    window.location.reload(true)
                  })
                }, 2000)
              }, 1500)
            })
          } else {
            $ionicLoading.hide();
            $ionicPopup.alert({
              title: 'Wrong Password',
              template: 'Try again...'
            })
          }
        }


      }
    })


    /** @ngInject */
    /*DeveloperCtrl*/
    .controller('DeveloperListsCtrl', function ($scope) {
      $scope.devlists = [
        {title: 'Ms.Chanakan Sitthinon', id: 1},
        {title: 'Mr.Narutchai Pipatwasukun', id: 2}
      ];
    })


    /**@ngInject */
    .controller('StafflistController', function (SearchStaffService,UserRoleService, $ionicPopover, $ionicPopup, $state, $scope, $rootScope, UserControlService, $ionicHistory, $ionicLoading, $timeout) {

      $scope.UserSearch='';
      $scope.$on('$ionicView.enter', function () {
        $ionicLoading.show({
          content: '<i class="icon ion-loading"></i>',
          template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Loading..</p>'
        });
        if ($rootScope.user != null) {
          UserControlService.query(function (data) {
            $scope.stafflist = data;
            $ionicLoading.hide()
          }, function (error) {
            $ionicPopup.alert({
              title: 'Failed!',
              template: 'Please check your internet connection or restart application'
            }).then(function () {
              $timeout(function () {
                $ionicLoading.hide();
                $ionicHistory.clearHistory();
                $ionicHistory.clearCache();
                $state.go('login')
              }, 1500)
            })
          }).$promise
        } else {
          $ionicLoading.hide()
          $ionicHistory.clearCache()
          $ionicHistory.clearHistory()
          $state.go('login')
        }

      });

      $scope.Filter = 'department';
      $scope.choose = 'Position';
      // $scope.MySelected = function (select) {
      //   $scope.Filter = select;
      //   console.log("First..." + $scope.Filter)
      //   if (select == 'department') {
      //     $scope.Filter = select;
      //     $scope.choose = 'Position'
      //     console.log($scope.Filter)
      //   } else if (select == 'id') {
      //     $scope.Filter = select;
      //     $scope.choose = 'Staff ID'
      //     console.log($scope.Filter)
      //   }
      // }

      $scope.numberOfItemsToDisplay = 1; // Use it with limit to in ng-repeat
      $scope.addMoreItem = function(done) {
        if ($scope.stafflist.length > $scope.numberOfItemsToDisplay)
          $scope.numberOfItemsToDisplay += 5; // load number of more items
        $scope.$broadcast('scroll.infiniteScrollComplete')
      }


      var timer=false;
      $scope.search = function (UserSearch) {
        console.log("Changed")
        console.log(UserSearch);
        console.log($scope.UserSearch+"1")
        if(timer){
          $timeout.cancel(timer)
        }
        timer= $timeout(function(){
          SearchStaffService.query({input:UserSearch},function (result) {
            $scope.stafflist = result;
          })
        },1000)
      }

      // $scope.Addadminrole = function (user) {
      //   $scope.staff = user
      //   $ionicLoading.show({
      //     content: '<i class="icon ion-loading"></i>',
      //     template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Loading..</p>'
      //   });
      //   UserRoleService.update($scope.staff).$promise.then(function (res) {
      //     $timeout(function () {
      //       UserControlService.query(function (data) {
      //         $scope.stafflist = data;
      //       })
      //       $ionicLoading.hide();
      //     }, 1500)
      //
      //   }, function (error) {
      //     console.log(error);
      //     $ionicLoading.hide();
      //   })
      // }

      // $scope.Removeadminrole = function (userId, roleId) {
      //   console.log(userId)
      //   console.log(roleId)
      //   $ionicLoading.show({
      //     content: '<i class="icon ion-loading"></i>',
      //     template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Loading..</p>'
      //   });
      //   UserRoleService.delete({userid: userId, roleid: roleId}).$promise.then(function (res) {
      //     $timeout(function () {
      //       UserControlService.query(function (data) {
      //         $scope.stafflist = data;
      //         console.log(res)
      //       })
      //       $ionicLoading.hide();
      //     }, 1500)
      //   }, function (error) {
      //     console.log(error)
      //     $ionicLoading.hide();
      //   })
      // }

    })

    /**@ngInject */
    .controller('employeeInfoController', function ($scope, $http, $routeParams, $route, UserControlService) {
      $scope.$on('$ionicView.loaded', function (event, data) {
        console.log("State Params: ", data.stateParams.id);
        var UserId = data.stateParams.id
        console.log(UserId)
        UserControlService.get({id: UserId}, function (data) {
          $scope.data = data;
        })
      })

      $scope.exportData = function () {
        var blob = new Blob([document.getElementById('exportable').innerHTML], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        saveAs(blob, "Report.xls");
      };

    })

})();
