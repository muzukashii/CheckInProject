(function () {
  'use strict';
  var API_PREFIX = 'http://localhost:8080';
  angular.module('ngResource+apiPrefix', ['ngResource'])
  angular.module('starter.controllers', [])
  angular.module('starter.services', [])
  angular.module('starter', ['ionic', 'ionic.service.core', 'ngCordova', 'ngResource',
    'ngCookies', 'starter.controllers', 'starter.services', 'flow', 'ngRoute', 'ui.router',
    'http-auth-interceptor', 'LocalStorageModule', 'cgBusy', 'ngAnimate', 'angular.filter','ionic-modal-select'])

    .config(configFailRequestRedirect)

    .config(configFlowFactoryProvider)

    .decorate('$resource', function ($delegate) {
      return function decoratedResource() {
        arguments[0] = API_PREFIX + arguments[0];
        return $delegate.apply(this, arguments);
      }
    })



    .config(['$compileProvider', function ($compileProvider) {
      $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|chrome-extension):/);
      $compileProvider.imgSrcSanitizationWhitelist(/^\s*(https?|local|data):/);
    }])

    .run(function ($ionicPlatform) {
      $ionicPlatform.ready(function () {
        // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
        // for form inputs)
        if (window.cordova && window.cordova.plugins.Keyboard) {
          cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
          cordova.plugins.Keyboard.disableScroll(true);

        }
        if (window.StatusBar) {
          // org.apache.cordova.statusbar required
          StatusBar.styleDefault();
        }
      });
    })

    .run(function ($rootScope, $state, $location, $cookies, $ionicPopup, $ionicLoading, $timeout, $ionicHistory, UserService,AutoLoginService) {
      var removeErrorMsg = $rootScope.$on('$viewContentLoaded', function () {
        delete $rootScope.error;
      });
      removeErrorMsg();

      $rootScope.logout = function () {
        var confirmPopup = $ionicPopup.confirm({
          title: 'Are you sure?',
          template: 'I need to ask again for confirmation'
        });
        confirmPopup.then(function (res) {
          if (res) {
            console.log("He need to log out");
            $ionicLoading.show({
              template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color:white">Log Out...</p>'
            });
            delete $rootScope.user;
            window.localStorage.clear();
            $timeout(function () {
              $ionicHistory.clearHistory();
              $ionicHistory.clearCache();
              $ionicHistory.nextViewOptions({
                disableBack: true,
                historyRoot: true
              })
            }, 2000);
            $state.go('login')
            $timeout(function () {
              window.location.reload(true)
              $ionicLoading.hide();
            }, 2000)
          } else {
            console.log("not log out")
          }
        });

      };

      $rootScope.hasRole = function (role) {
        if ($rootScope.user == undefined) {
          return false;
        }

        if ($rootScope.user.roles[role] == undefined) {
          return false;
        }

        return $rootScope.user.roles[role];
      }

      $rootScope.$on('$ionicView.loaded', function (event, data) {
        var userLocalStorage = window.localStorage.getItem("Cookies");
        if (data.stateName != 'register') {
          if ($rootScope.user == null || userLocalStorage == null) {
            event.preventDefault();
            $state.go('login')
          }
        }
      })


      var userLocalStorage = window.localStorage.getItem("Cookies");
      if (userLocalStorage !== undefined && userLocalStorage !== null) {
        $ionicLoading.show({
          template: '<ion-spinner class="spinner-balanced"></ion-spinner><p style="color:white">Loading...</p>'
        })
        window.localStorage.clear();
        AutoLoginService.get({username: userLocalStorage}
        , function (user) {
          $rootScope.user = user;
          $ionicHistory.clearHistory();
          $ionicHistory.clearCache();
          $ionicHistory.nextViewOptions({
            disableBack: true,
            historyRoot: true
          })
          window.localStorage.setItem("Cookies", user.username);
          $timeout(function () {
            $ionicLoading.hide();
            $state.go('app.map')
          }, 5000)
        }, function (error) {
          window.localStorage.clear();
          delete $rootScope.user;
          $ionicHistory.clearHistory;
          $ionicHistory.clearCache();
          $ionicHistory.nextViewOptions({
            disableBack: true,
            historyRoot: true
          })
          console.log(error)
          $ionicLoading.hide();
          $state.go('login')
        })
      } else {
        $ionicHistory.nextViewOptions({
          disableBack: true,
          historyRoot: true
        })
        $ionicHistory.clearHistory();
        $ionicLoading.hide();
        $state.go('login')
      }

    })

    .config(function ($ionicConfigProvider) {
      $ionicConfigProvider.navBar.alignTitle('center')
    })

    .config(function ($stateProvider, $urlRouterProvider,$locationProvider) {
      $stateProvider

        .state('login', {
          url: '/login',
          cache: false,
          templateUrl: 'templates/login.html',
          controller: 'LoginController'
        })

        .state('register', {
          url: '/register',
          cache: false,
          templateUrl: 'templates/register.html',
          controller: 'registerController'
        })

        .state('app', {
          url: '/app',
          templateUrl: 'templates/menu.html',
          controller: 'MenuCtrl'
        })

        .state('app.map', {
          url: '/home',
          cache: false,
          views: {
            'menuContent': {
              templateUrl: 'templates/Home.html',
              controller: 'MapCtrl'
            }
          }
        })

        .state('app.adminMenu', {
          url: '/adminMenu',
          views: {
            'menuContent': {
              templateUrl: 'templates/adminMenu.html'
            }
          }
        })

        .state('app.stafflist', {
          url: '/stafflist',
          views: {
            'menuContent': {
              templateUrl: 'templates/stafflist.html',
              controller: 'StafflistController'
            }
          }
        })

        .state('app.userInfo',{
          url:'/userInfo/:id',
          views:{
            'menuContent':{
              templateUrl:'templates/userInfo.html',
              controller:'employeeInfoController'
            }
          }
        })

        .state('app.manageAccount', {
          url: '/manageAccount',
          views: {
            'menuContent': {
              templateUrl: 'templates/manageAccount.html'
            }
          }
        })

        .state('app.editAccount', {
          url: '/editAccount',
          cache: false,
          views: {
            'menuContent': {
              templateUrl: 'templates/editAccount.html',
              controller: 'editAccountController'
            }
          }
        })

        .state('app.history', {
          url: '/history/:id',
          cache: false,
          views: {
            'menuContent': {
              templateUrl: 'templates/history.html',
              controller:'employeeInfoController'
            }
          }
        })

        .state('app.AboutUs', {
          url: '/AboutUs',
          views: {
            'menuContent': {
              templateUrl: 'templates/AboutUs.html',
              controller: 'DeveloperListsCtrl'
            }
          }
        })

      // if none of the above states are matched, use this as the fallback
      $urlRouterProvider.otherwise("/login");
    })


  /** @ngInject */
  function configFlowFactoryProvider(flowFactoryProvider) {
    flowFactoryProvider.defaults = {
      target: '',
      permanentErrors: [500, 501],
      maxChunkRetries: 1,
      chunkRetryInterval: 5000,
      simultaneousUploads: 4,
      singleFile: true
    };


    // flowFactoryProvider.on('catchAll', function ($log) {
    //   console.log('catchAll', arguments);
    // });
    // Can be used with different implementations of Flow.js
    // flowFactoryProvider.factory = fustyFlowFactory;
  }


  function configFailRequestRedirect($httpProvider) {
    /* Register error provider that shows message on failed requests or redirects to login page on
     * unauthenticated requests */
    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
      return {
        'responseError': function (rejection) {
          var status = rejection.status;
          var config = rejection.config;
          var method = config.method;
          var url = config.url;

          if (status == 401) {
            $location.path("/login");
          } else {
            $rootScope.error = method + " on " + url + " failed with status " + status;
          }
          return $q.reject(rejection);
        }
      }
    });

    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
     * as soon as there is an authenticated user */
    var exampleAppConfig = {
      /* When set to false a query parameter is used to pass on the auth token.
       * This might be desirable if headers don't work correctly in some
       * environments and is still secure when using https. */
      useAuthTokenHeader: true
    };

    $httpProvider.interceptors.push(function ($q, $rootScope) {
      return {
        'request': function (config) {
          if (angular.isDefined($rootScope.authToken)) {
            var authToken = $rootScope.authToken;
            if (exampleAppConfig.useAuthTokenHeader) {
              config.headers['X-Auth-Token'] = authToken;
            } else {
              config.url = config.url + "?token=" + authToken;
            }
          }
          return config || $q.when(config);
        }
      }
    })
  }


})();
