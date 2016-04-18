(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi',
    [
      'ngAnimate',
      'ngTouch',
      'ngSanitize',
      'ngMessages',
      'ngResource',
      'ui.router',
      'ui.bootstrap',
      'toastr',

      'crudmicroservicesUi.core'
    ]);
})();
