(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log) {

    $log.debug('runBlock end');
  }

})();
