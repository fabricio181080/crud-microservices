(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .constant('personsConfig', {
      mock : {
        list : '/api/persons',
        edit : /\/api\/persons\/(\d+)/
      }
    })
    .run(updateConfig);

  function updateConfig(personsConfig, api) {
    personsConfig.URL = api.concat('/persons/:id');
  }

})();
