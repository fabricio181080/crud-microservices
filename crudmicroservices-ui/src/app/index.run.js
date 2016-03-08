(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log, $httpBackend) {

    $log.debug('runBlock end');

    $httpBackend.whenGET(/^app\/.*/).passThrough();
  }

})();
