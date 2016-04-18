(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .controller('PersonsController', PersonsController);

  function PersonsController(Persons) {
    var vm = this;

    // public methods
    vm.refresh = list;
    vm.remove = remove;
    // properties
    vm.totalItems = 0;
    vm.items = [];

    // init
    list();

    // implements
    function list() {
      Persons.query().$promise
        .then(function (data) {
          vm.items = data.assets;
          vm.totalItems = data.totalAssets;
          vm.updateDate = new Date();
        });
    }

    function remove(item) {
      Persons.remove({id : item.id}).$promise.then(list);
    }
  }

})();
