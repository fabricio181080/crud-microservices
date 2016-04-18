(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .service('resourceAPI', resourceAPI);

  function resourceAPI($resource) {
    return function (url, properties, methods) {
      properties = properties || {id: '@id'};

      methods = angular.extend({
        update: {method: 'PUT'},
        query: {isArray : false}
      }, methods);

      return $resource(url, properties, methods);
    };

  }
})();
