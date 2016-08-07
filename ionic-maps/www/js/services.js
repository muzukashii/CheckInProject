(function () {
  'use strict'
  angular
    .module('starter.services')
    .factory('UserService', UserService)
    .factory('locationService', locationService)
    .factory('UserControlService', UserControlService)
    .factory('RemoveImageService', RemoveImageService)
    .factory('CompanyRoleService', CompanyRoleService)
    .factory('AutoLoginService',AutoLoginService)
    .factory('UserRoleService',UserRoleService)
    .factory('ValidateService',ValidateService)


  /** @ngInject */
  function locationService($resource) {
    return $resource('/checkin/:id', {id: '@_id'}, {
      update: {
        method: 'PUT' // this method issues a PUT request
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
  function ValidateService($resource) {
    return $resource('/ValidateEmail', {}, {
      update: {
        method: 'POST' // this method issues a PUT request
      }
    });
  };

  /** @ngInject */
  function UserService($resource) {
    return $resource('/login', {}, {
      update: {
        method: 'POST' // this method issues a PUT request
      }
    });
  };

  /** @ngInject */
  function CompanyRoleService($resource) {
    return $resource('/companyrole/:id', {id: '@_id'}, {
      update: {
        method: 'GET' // this method issues a PUT request
      }
    });
  };

  /** @ngInject */
  function RemoveImageService($resource) {
    return $resource('/userimage/remove:id', {id: '@_id'}, {
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
  };

})();
