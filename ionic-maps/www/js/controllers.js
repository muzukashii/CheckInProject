(function () {
  'use strict'
  angular
    .module('starter.controllers', [])


    /** @ngInject */
    .controller('MenuCtrl', function ($ionicSideMenuDelegate, $ionicPopup, RemoveImageService, $http, $scope, $ionicBackdrop, $rootScope, UserControlService, $ionicLoading, $ionicHistory, $timeout, UserService) {

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
              $timeout(function () {
                var UserId = $scope.userMenu.id;
                // set location
                flowFiles.opts.target = 'http://localhost:8080/userimage/add';
                flowFiles.opts.testChunks = false;
                flowFiles.opts.query = {UserId: UserId};
                flowFiles.upload();
                $timeout(function () {
                  UserService.get({username: $scope.userMenu.username}
                    , function (user1) {
                      $rootScope.user = user1;
                      $ionicLoading.hide();
                    })
                }, 1500)
              }, 2500)
            })
          }, 2000)
        } else {
          $timeout(function () {
            var UserId = $scope.userMenu.id;
            // set location
            flowFiles.opts.target = 'http://localhost:8080/userimage/add';
            flowFiles.opts.testChunks = false;
            flowFiles.opts.query = {UserId: UserId};
            flowFiles.upload();
            $timeout(function () {
              UserService.get({username: $scope.userMenu.username}
                , function (user1) {
                  $timeout(function () {
                    $rootScope.user = user1;
                    $ionicLoading.hide();
                  }, 2000)
                })
            }, 1500)
          }, 2000)
        }
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
      var vm = this;

      function serializeData(data) {
        // If this is not an object, defer to native stringification.
        if (!angular.isObject(data)) {
          return ( ( data == null ) ? "" : data.toString() );
        }

        var buffer = [];

        // Serialize each key in the object.
        for (var name in data) {
          if (!data.hasOwnProperty(name)) {
            continue;
          }

          var value = data[name];

          buffer.push(
            encodeURIComponent(name) + "=" + encodeURIComponent(( value == null ) ? "" : value)
          );
        }

        // Serialize the buffer and clean it up for transportation.
        var source = buffer.join("&").replace(/%20/g, "+");
        return ( source );
      }


      vm.doLogin = function () {
        $ionicLoading.show({
          template: '<ion-spinner class="spinner-spiral"></ion-spinner><p style="color:white">Loading...</p>'
        });
        UserService.get({username: vm.username}
          , function (user) {
            var key, count = 0;
            for (key in user) {
              if (user.hasOwnProperty(key)) {
                count++;
              }
            }
            if (count > 2) {
              if (vm.password == user.password) {
                $rootScope.user = user;
                window.localStorage.setItem("Cookies", user.username);
                $timeout(function () {
                  $ionicLoading.hide();
                  $ionicHistory.clearHistory();
                  $state.go('app.map')
                }, 5000)
              } else {
                $ionicLoading.hide();
                $ionicPopup.alert({
                  title: 'Failed!',
                  template: 'Username or Password is incorrect...'
                }).then(function () {
                  $timeout(function () {
                    $ionicHistory.clearHistory();
                    $ionicHistory.clearCache();
                  }, 1500)
                })
              }
            } else {
              $ionicLoading.hide();
              $ionicPopup.alert({
                title: 'Failed!',
                template: 'Username or Password is incorrect...'
              }).then(function () {
                $timeout(function () {
                  $ionicHistory.clearHistory();
                  $ionicHistory.clearCache();
                }, 1500)
              })
            }
          })
      }

      $scope.register = function () {
        $state.go('register', {}, {reload: true});
      };

    })


    /** @ngInject */
    .controller('MapCtrl', function ($ionicSideMenuDelegate, $scope, $rootScope, $ionicBackdrop, $state, $cordovaGeolocation, $http, $ionicPopup, locationService, $ionicLoading, $ionicHistory, $timeout) {

      $scope.$on('$ionicView.enter', function () {
        $ionicSideMenuDelegate.canDragContent(false);
      });
      $scope.$on('$ionicView.leave', function () {
        $ionicSideMenuDelegate.canDragContent(true);
      });

      var options = {timeout: 10000, enableHighAccuracy: true};

      var GeoCoder = new google.maps.Geocoder;
      $scope.prev_infowindow = false;

      window.CheckIn = function (name, type) {
        $scope.usercheck = $rootScope.user;
        console.log($scope.usercheck)
        $ionicLoading.show({
          content: '<i class="icon ion-loading"></i>',
          template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Please wait...</p>'
        });
        $scope.date = new Date();
        $scope.checkinData = {};
        $scope.checkinData.time = $scope.date;
        $scope.checkinData.location = name;
        $scope.checkinData.type = type;
        console.log($scope.checkinData)
        locationService.save({UserId: $scope.usercheck.id}, $scope.checkinData, function (data) {
          $ionicLoading.hide();
          $ionicPopup.alert({
            title: 'Success!',
            template: 'Check in successfully'
          })
          console.log(data)
        }, function (error) {
          $ionicLoading.hide();
          $ionicPopup.alert({
            title: 'Failed!',
            template: 'Please check your internet connection'
          })
        })

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

        google.maps.event.addListener(marker2, 'click', function () {
          $scope.Clockin = 'Clock in';
          $scope.Clockout = 'Clock out'
          infoWindow2.setContent('<div class="infoWindowS"><p style="font-size:18px;padding-top: 20px;"> Place name : ' + place.name + '</p>' +
            '<button class="button button-positive" style="margin: 15px" ' +
            'onclick="CheckIn(\'' + place.name + '\',\'' + $scope.Clockin + '\')"> Clock In </button><button class="button button-assertive" style="margin:15px" ' +
            'onclick="CheckIn(\'' + place.name + '\',\'' + $scope.Clockout + '\')"> Clock Out </button></div>');
          if ($scope.prev_infowindow) {
            $scope.prev_infowindow.close()
          }
          $scope.prev_infowindow = infoWindow2;
          infoWindow2.open(map, this);
        });
      }

      function CenterControl(controlDiv, map) {

        // Set CSS for the control border.
        var controlUI = document.createElement('div');
        controlUI.style.backgroundColor = '#fff';
        controlUI.style.border = '2px solid #fff';
        controlUI.style.borderRadius = '3px';
        controlUI.style.boxShadow = '0 2px 6px rgba(0,0,0,.3)';
        controlUI.style.cursor = 'pointer';
        controlUI.style.marginBottom = '22px';
        controlUI.style.textAlign = 'center';
        controlUI.title = 'Click to recall current location';
        controlDiv.appendChild(controlUI);

        // Set CSS for the control interior.
        var controlText = document.createElement('div');
        controlText.style.color = 'rgb(25,25,25)';
        controlText.style.fontFamily = 'Roboto,Arial,sans-serif';
        controlText.style.fontSize = '16px';
        controlText.style.lineHeight = '38px';
        controlText.style.paddingLeft = '5px';
        controlText.style.paddingRight = '5px';
        controlText.innerHTML = '<i class="icon ion-location"></i> Recall my location';
        controlUI.appendChild(controlText);

        // Setup the click event listeners: simply set the map to Chicago.
        controlUI.addEventListener('click', function () {
          initialize();
        });

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

        var centerControlDiv = document.createElement('div');
        var centerControl = new CenterControl(centerControlDiv, map);
        centerControlDiv.index = 1;
        map.controls[google.maps.ControlPosition.TOP_CENTER].push(centerControlDiv);

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

      function initialize() {
        var mapOptions = {
          center: new google.maps.LatLng(15.8700320, 100.9925410),
          zoom: 6,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        map = new google.maps.Map(document.getElementById("map"), mapOptions);
        $scope.map = map;
        var centerControlDiv = document.createElement('div');
        var centerControl = new CenterControl(centerControlDiv, map);
        centerControlDiv.index = 1;
        map.controls[google.maps.ControlPosition.TOP_CENTER].push(centerControlDiv);


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
    .controller('registerController', function ($ionicPlatform,$cordovaDevice,$timeout, $ionicLoading, CompanyRoleService, $scope, $rootScope, $window, UserControlService, $ionicPopup, $state, $ionicHistory) {

        document.addEventListener("deviceready", function () {

          var device = $cordovaDevice.getDevice();
          $scope.manufacturer = device.manufacturer;
          $scope.model = device.model;
          $scope.platform = device.platform;
          $scope.uuid = device.uuid;
          $scope.allinfo = device;

          var cordova = $cordovaDevice.getCordova();

          var model = $cordovaDevice.getModel();

          var platform = $cordovaDevice.getPlatform();

          var uuid = $cordovaDevice.getUUID();

          var version = $cordovaDevice.getVersion();

        }, false);



      $scope.form = document.getElementById("registerform");
      $scope.pic = false;
      $scope.user = {};

      $scope.$on('$ionicView.enter', function () {
        $ionicLoading.show({
          content: '<i class="icon ion-loading"></i>',
          template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Loading..</p>'
        });
        $timeout(function () {
          $ionicLoading.hide()
        }, 2000)
      });

      $scope.queryPromise = CompanyRoleService.query(function (data) {
        $scope.rolelist = data;
        $scope.user = {companyrole: 'Chairman'}
      })


      $scope.sendRegister = function (flowFiles) {
        console.log($scope.user.companyrole)
        console.log($scope.user);
        UserControlService.save($scope.user, function (data) {
          var UserId = data.id;
          // set location
          flowFiles.opts.target = 'http://localhost:8080/userimage/add';
          flowFiles.opts.testChunks = false;
          flowFiles.opts.query = {UserId: UserId};
          flowFiles.upload();
          $ionicLoading.show({
            content: '<i class="icon ion-loading"></i>',
            template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Loading...</p>'
          });
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
                $ionicHistory.goBack()
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
      $scope.validate = function (file) {
        console.log(file)
        $scope.errors = [];
        if ((file.size / 1000) > 1024) {
          $scope.errors.push({file: file, error: "File is too big"});
          $scope.pic = false;
          return false;
        }
        $scope.errors.push({file: file, error: "Passed"});
        $scope.pic = true;
        return true;
      }

      $scope.recheck = function () {
        $scope.pic = false;
        $scope.errors.splice(0, 1);
        return false
      }

      $scope.resetForm = function () {
        console.log("Hap")
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
    .controller('editAccountController', function (CompanyRoleService, $ionicHistory, RemoveImageService, $timeout, UserService, $scope, $rootScope, $ionicPopup, UserControlService, $ionicLoading) {


      $scope.usernew = {}
      $scope.Check = null;
      $scope.pic = false;

      $scope.usernew.email = $rootScope.user.email;
      $scope.usernew.tel = $rootScope.user.tel;
      $scope.usernew.companyrole = $rootScope.user.companyrole;
      $scope.usernew.name = $rootScope.user.name;


      $scope.$on('$ionicView.enter', function () {
        $ionicLoading.show({
          content: '<i class="icon ion-loading"></i>',
          template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Loading..</p>'
        });
        $scope.queryPromise = CompanyRoleService.query(function (data) {
          $scope.rolelist = data;
        })
        $timeout(function () {
          $ionicLoading.hide()
        }, 2000)
      });


      $scope.validate = function (file) {
        console.log(file)
        $scope.errors = [];
        if ((file.size / 1000) > 1024) {
          $scope.errors.push({file: file, error: "File is too big"});
          $scope.pic = false;
          return false;
        }
        $scope.errors.push({file: file, error: "Passed"});
        $scope.pic = true;
        return true;
      }

      $scope.recheck = function () {
        $scope.pic = false;
        $scope.errors.splice(0, 1);
        return false
      }


      $scope.updateInfo = function (flowFiles) {
        if ($rootScope.user != null) {
          $scope.usernew = {
            id: $rootScope.user.id,
            name: $scope.usernew.name,
            username: $rootScope.user.username,
            email: $scope.usernew.email,
            password: $rootScope.user.password,
            roles: $rootScope.user.roles,
            tel: $scope.usernew.tel,
            companyrole: $scope.usernew.companyrole
          }
          console.log($scope.usernew);
          $ionicLoading.show({
            template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color:white">Please wait...</p>'
          });
          if ($scope.Check == $rootScope.user.password) {
            console.log($scope.Check)
            console.log($rootScope.user.password)
            if ($rootScope.user.images.length > 0) {
              $scope.imgId = $rootScope.user.images[0].id;
              $timeout(function () {
                RemoveImageService.delete({userid: $rootScope.user.id, imageid: $scope.imgId}).then(function () {
                  delete $scope.usernew.images
                  UserControlService.update({id: $scope.usernew.id}, $scope.usernew, function (user2) {
                    console.log(user2)
                    var UserId = user2.id;
                    // set location
                    flowFiles.opts.target = 'http://localhost:8080/userimage/add';
                    flowFiles.opts.testChunks = false;
                    flowFiles.opts.query = {UserId: UserId};
                    flowFiles.upload();
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
                })
              }, 2000)
            } else {
              UserControlService.update({id: $scope.usernew.id}, $scope.usernew, function (user2) {
                console.log(user2)
                var UserId = user2.id;
                // set location
                flowFiles.opts.target = 'http://localhost:8080/userimage/add';
                flowFiles.opts.testChunks = false;
                flowFiles.opts.query = {UserId: UserId};
                flowFiles.upload();
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
                  }, 1000)
                }, 1000)
              })
            }

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
    .controller('StafflistController', function (RemoveRoleService, AddRoleService, $ionicPopover, $ionicPopup, $state, $scope, $rootScope, UserControlService, $ionicHistory, $ionicLoading, $timeout) {

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

      $scope.Addadminrole = function (userid, user) {
        $scope.staff = user
        $ionicLoading.show({
          content: '<i class="icon ion-loading"></i>',
          template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Loading..</p>'
        });
        AddRoleService.update({id: userid}, $scope.staff).$promise.then(function (res) {
          $timeout(function () {
            UserControlService.query(function (data) {
              $scope.stafflist = data;
            })
            $ionicLoading.hide();
          }, 1500)

        }, function (error) {
          console.log(error);
          $ionicLoading.hide();
        })
      }

      $scope.Removeadminrole = function (userid, roleid) {
        console.log(userid)
        console.log(roleid)
        $ionicLoading.show({
          content: '<i class="icon ion-loading"></i>',
          template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color: white">Loading..</p>'
        });
        RemoveRoleService.delete({userid: userid, roleid: roleid}).$promise.then(function (res) {
          $timeout(function () {
            UserControlService.query(function (data) {
              $scope.stafflist = data;
              console.log(res)
            })
            $ionicLoading.hide();
          }, 1500)
        }, function (error) {
          console.log(error)
          $ionicLoading.hide();
        })
      }

    })


})();
