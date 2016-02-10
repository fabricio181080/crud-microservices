(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .service('Persons', Persons);

  function Persons(resourceAPI, personsConfig) {
    return resourceAPI(personsConfig.URL);
  }

})();
