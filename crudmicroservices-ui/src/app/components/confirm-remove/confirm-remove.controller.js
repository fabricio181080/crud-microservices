(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .controller('ConfirmRemoveController', ConfirmRemoveController);

    function ConfirmRemoveController($uibModalInstance) {

      var vm = this;

      vm.ok = ok;
      vm.cancel = cancel;

      function ok() {
        $uibModalInstance.close();
      }

      function cancel() {
        $uibModalInstance.dismiss();
      }
    }

})();
