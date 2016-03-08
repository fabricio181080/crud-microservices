(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi.core', ['ngMockE2E'])
    .run(runBlock);

  /** @ngInject */
  function runBlock($httpBackend) {
    $httpBackend.whenGET(/^app\/.*/).passThrough();
  }

})();
