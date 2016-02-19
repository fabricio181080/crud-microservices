(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .controller('PersonEditController', PersonEditController);

  function PersonEditController(Persons, $stateParams, $state, toastr) {

    var vm = this;

    vm.save = save;

    vm.model = Persons.get({id : $stateParams.id});

    vm.model.$promise.catch(function () {
      toastr.error('Failed to load person.', 'Error');
      $state.go('^');
    });

    function save() {
      vm.model.$update().then(function () {
        toastr.success('Person saved', 'Success');
        $state.go('^');
      });
    }
  }

})();
