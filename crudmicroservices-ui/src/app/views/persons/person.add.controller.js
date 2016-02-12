(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .controller('PersonAddController', PersonAddController);

  function PersonAddController($state, Persons, toastr) {
    var vm = this;

    vm.save = save;

    vm.model = new Persons();

    function save() {
      vm.model.$save().then(function () {
        toastr.success('Person saved', 'Success');
        $state.go('^');
      });
    }
  }

})();
