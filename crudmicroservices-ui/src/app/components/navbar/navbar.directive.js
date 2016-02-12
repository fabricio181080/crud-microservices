(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .directive('navbar', navbar);

  /** @ngInject */
  function navbar() {
    var directive = {
      restrict: 'A',
      templateUrl: 'app/components/navbar/navbar.html'
    };

    return directive;
  }

})();
