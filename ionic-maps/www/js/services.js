(function () {
  'use strict'
  angular
    .module('starter.services')
    .factory('UserService', UserService)
    .factory('locationService', locationService)
    .factory('UserControlService', UserControlService)
    .factory('RemoveImageService', RemoveImageService)
    .factory('DepartmentService', DepartmentService)
    .factory('AutoLoginService',AutoLoginService)
    .factory('UserRoleService',UserRoleService)
    .factory('verifyEmailService',verifyEmailService)
    .factory('DailyCheckService',DailyCheckService)
    .factory('SearchStaffService',SearchStaffService)


  /** @ngInject */
  function locationService($resource) {
    return $resource('/checkin/:id', {id: '@_id'}, {
      update: {
        method: 'PUT' // this method issues a PUT request
      }
    });
  };

  /** @ngInject */
  function SearchStaffService($resource) {
    return $resource('/search', {}, {
      update: {
        method: 'GET' // this method issues a PUT request
      }
    });
  };

  /** @ngInject */
  function DailyCheckService($resource) {
    return $resource('/dailyCheck/:id', {id: '@_id'}, {
      update: {
        method: 'GET' // this method issues a PUT request
      }
    });
  };

  /** @ngInject */
  function UserControlService($resource) {
    return $resource('/userControl/:id', {id: '@_id'}, {
      update: {
        method: 'PUT' // this method issues a PUT request
      }
    });
  };

  /** @ngInject */
  function verifyEmailService($resource) {
    return $resource('/verifyEmail', {}, {
      update: {
        method: 'GET' // this method issues a PUT request
      }
    });
  };

  /** @ngInject */
  function UserService($resource) {
    return $resource('/login', {}, {
      update: {
        method: 'GE' // this method issues a PUT request
      }
    });
  };

  /** @ngInject */
  function DepartmentService($resource) {
    return $resource('/department/:id', {id: '@_id'}, {
      update: {
        method: 'GET' // this method issues a PUT request
      }
    });
  };

  /** @ngInject */
  function RemoveImageService($resource) {
    return $resource('/userImage/remove:id', {id: '@_id'}, {
      update: {
        method: 'DELETE' // this method issues a PUT request
      }
    });
  };

  /** @ngInject */
  function UserRoleService($resource) {
    return $resource('/role/:id', {id: '@_id'}, {
      update: {
        method: 'PUT' // this method issues a PUT request
      }
    });
  }

  /** @ngInject */
  function AutoLoginService($resource) {
    return $resource('/autoLogin', {}, {
        update: {
          method: 'GET' // this method issues a PUT request
        }
      });
  }

})();
