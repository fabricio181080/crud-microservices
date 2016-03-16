(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .directive('confirmRemove', confirmRemove);

  /** @ngInject */
  function confirmRemove($uibModal, $log, $parse) {
    var directive = {
      restrict: 'A',
      link: link
    };

    return directive;

    function link ($scope, elem, attrs) {
      elem.on('click', function() {
        var modalInstance = $uibModal.open({
          templateUrl: 'app/components/confirm-remove/confirm-remove.html',
          controller: 'ConfirmRemoveController',
          controllerAs: 'ctrl'
        });

        var callback = $parse(attrs.confirmRemove);

        modalInstance.result.then(function () {
          callback($scope);
        });
      });
    }
  }

})();
