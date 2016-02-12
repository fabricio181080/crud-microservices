(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .controller('PersonEditController', PersonEditController);

  function PersonEditController(Persons, $stateParams, $state, toastr) {
    var vm = this;

    vm.save = save;

    vm.model = Persons.get($stateParams.id)
      .$promise.catch(function () {
        toastr.error('Failed to load person.', 'Error');
        $state.go('^');
      });

    function save() {
      vm.model.$save().then(function () {
        toastr.success('Person saved', 'Success');
        $state.go('^');
      });
    }
  }

})();
