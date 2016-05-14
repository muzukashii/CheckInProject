angular.module('starter.controllers', ['ionic','ngCordova'])

.controller('AppCtrl', function($scope, $ionicModal, $timeout) {

  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  // Form data for the login modal
  $scope.loginData = {};

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    console.log('Doing login', $scope.loginData);

    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
    }, 1000);
  };
})

.controller('DeveloperListsCtrl', function($scope) {
  $scope.devlists = [
    { title: 'Mr.Pranpoom Dissayakamon', id: 1 },
    { title: 'Mr.Narutchai Pipatwasukun', id: 2 },
    { title: 'Mr.Peerapat Jommanee', id: 3 }
  ];
})

.controller('DeveloperListCtrl', function($scope, $stateParams) {

})

  .controller('MapCtrl', function($scope, $state,$cordovaGeolocation,$http,$ionicPopup) {
    var options = {timeout: 10000, enableHighAccuracy: true};

    var GeoCoder = new google.maps.Geocoder;
    var map = null;
    var marker = null;
    function geocodePosition(pos) {

      GeoCoder.geocode({latLng: pos}, function(responses) {
        if(responses && responses.length > 0){
          // alert(responses[0].geometry.location);
          var locationSearch = responses[0].geometry.location;
          PlaceID = responses[0].place_id;
          var service = new google.maps.places.PlacesService(map);
          service.nearbySearch({
            location: locationSearch,
            radius: 200
          }, function (results, status) {
            if (status === google.maps.places.PlacesServiceStatus.OK) {
              for (var i = 0; i < results.length; i++) {
                createMarker(results[i]);
              }
            }
          })
          function createMarker(place) {
            var placeLoc = place.geometry.location;
            var infoWindow2 = new google.maps.InfoWindow();
            // var photos = place.photos;
            var marker2 = new google.maps.Marker({
              map: map,
              position:placeLoc
            });
            // if(!photos){
            //   photos=null;
            //   return;
            // }
            google.maps.event.addListener(marker2, 'click', function() {
              infoWindow2.setContent('<p style="font-size:15px"> Place name : '+ place.name + '</p>'+
                '<button class="button button-assertive" style="padding-top: 5px" ' +
                'onclick="CheckIn(\'' + place.name + '\')"> Check In </button>');
              infoWindow2.open(map, this);
            });
          }
          service.getDetails({
            placeId: PlaceID
          }, function (place,status) {
            if (status === google.maps.places.PlacesServiceStatus.OK) {
              updateMarkerAddress(place.name);
            } else {
              updateMarkerAddress('Cannot determine address at this location.');
            }
          });
        }
      });
    }
    function updateMarkerAddress(Pname) {
      infoWindow.setContent("คุณอยู่ที่นี่.."+ '<br>' + Pname);
    }
    window.CheckIn = function (name) {
      $scope.data={};
      $ionicPopup.show({
        template:'<input type="text" ng-model="data.link">',
        response:"Input here",
        title:'Enter ip address to Check in',
        subTitle:'Calm down',
        scope:$scope,
        buttons:[
          {text:'Cancel'},
          {
            text:'<b>Save</b>',
            type:'button-positive',
            onTap:function (e){
              if(!$scope.data.link){
                e.preventDefault();
              }else {
                if($scope.data.link!==null){
                  var res = JSON.stringify(name);
                  $http.get('http://'+$scope.data.link+':8080/mylocation?UserLocate='+res,{timeout:3000})
                    .then(function () {
                      $ionicPopup.alert({
                        title:'Result',
                        template:'Check in success'
                      })
                    }, function (response) {
                      $ionicPopup.alert({
                        title:'Result',
                        template:'Fail to login'+response
                      })
                    });
                }else{
                  $ionicPopup.alert({
                    title:'Result',
                    template:'Please enter ip address'
                  })
                }
                return $scope.data.link;
              }
            }

          }]
      })

    };


    var infoWindow = new google.maps.InfoWindow();
    function initialize() {
      $cordovaGeolocation.getCurrentPosition(options).then(function (position) {
        var latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

        var mapOptions = {
          center: latLng,
          zoom: 17,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById("map"), mapOptions);
        $ionicPopup.alert({
          title:'GPS service',
          subTitle:'Please make sure your GPS Service is enabled'
        });
        var icon = {
          url:"http://icons.iconarchive.com/icons/icons-land/vista-map-markers/256/Map-Marker-Flag-1-Right-Azure-icon.png",
          scaledSize: new google.maps.Size(50, 50), // scaled size
          origin: new google.maps.Point(0,0), // origin
          anchor: new google.maps.Point(0, 0) // anchor
        };
        marker = new google.maps.Marker({
          position: latLng,
          title: 'You are here',
          map: map,
          icon: icon
        });

        // Update current position info.
        geocodePosition(latLng);
        infoWindow.open(map, marker);

      })
    }
    google.maps.event.addDomListener(window, 'load', initialize);


  });

