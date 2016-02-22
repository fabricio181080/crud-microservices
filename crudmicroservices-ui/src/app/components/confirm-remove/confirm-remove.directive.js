(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .directive('confirmRemove', confirmRemove);

  /** @ngInject */
  function confirmRemove($uibModal, $log) {
    var directive = {
      restrict: 'A',
      link: link
    };

    return directive;

    function link ($scope, elem) {
      elem.on('click', function() {
        var modalInstance = $uibModal.open({
          templateUrl: 'app/components/confirm-remove/confirm-remove.html',
          controller: 'ConfirmRemoveController',
          controllerAs: 'ctrl'
        });

        modalInstance.result.then(function (item) {
          $scope.selected = item;
        }, function () {
          $log.info('Modal dismissed at: ' + new Date());
        });
      });
    }
  }

})();
