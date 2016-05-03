// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'

angular
  .module('starter', ['ionic', 'ngCordova'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('map', {
        url: '/',
        templateUrl: 'templates/map.html',
        controller: 'MapCtrl'
      });

    $urlRouterProvider.otherwise("/");

  })
  .controller('MapCtrl', function($scope, $state,$cordovaGeolocation,$http) {
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
            radius: 150
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
            var marker2 = new google.maps.Marker({
              map: map,
              position:placeLoc
            });
            google.maps.event.addListener(marker2, 'click', function() {
              infoWindow2.setContent(place.name + '<br><input type="submit" value="Check in" ' +
                'onclick="CheckIn(\'' + place.name + '\')">');
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
    window.CheckIn = function (name) {
      var link =  prompt("Enter ip address to Check in!")
      var res = JSON.stringify(name)
     $http.get('http://'+link+':8080/mylocation?UserLocate='+res,{timeout:3000})
       .then(function () {
         alert("Success to check in");
       }, function (response) {
         alert("failed " + response.status);
       });

    }


    function updateMarkerStatus(str) {
      infoWindow.setContent(str);
    }

    function updateMarkerAddress(str) {
      infoWindow.setContent(str);
    }
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
        marker = new google.maps.Marker({
          position: latLng,
          title: 'Find Place marker',
          map: map,
          draggable: true,
          icon:'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
        });

        // Update current position info.
        geocodePosition(latLng);

        // Add dragging event listeners.

        google.maps.event.addListener(marker, 'dragstart', function () {
          updateMarkerAddress('Dragging...');
        });

        google.maps.event.addListener(marker, 'dragend', function () {
          updateMarkerStatus('Drag ended');
         geocodePosition(marker.getPosition());
        });
        google.maps.event.addListener(marker, 'click', function () {
                infoWindow.open(map, marker);
              });
      })
    }
    google.maps.event.addDomListener(window, 'load', initialize);


  })

  .run(function ($ionicPlatform) {
    $ionicPlatform.ready(function () {
      if (window.cordova && window.cordova.plugins.Keyboard) {
        // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
        // for form inputs)
        cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);

        // Don't remove this line unless you know what you are doing. It stops the viewport
        // from snapping when text inputs are focused. Ionic handles this internally for
        // a much nicer keyboard experience.
        cordova.plugins.Keyboard.disableScroll(true);
      }
      if (window.StatusBar) {
        StatusBar.styleDefault();
      }
    });
  })


