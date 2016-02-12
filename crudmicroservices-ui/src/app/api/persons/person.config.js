(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .constant('personsConfig', {})
    .run(updateConfig);

  function updateConfig(personsConfig, api) {
    personsConfig.URL = api.concat('/persons/:id');
  }

})();
