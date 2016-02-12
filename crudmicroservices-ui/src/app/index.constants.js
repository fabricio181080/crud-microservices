/* global toastr:false */
(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .constant('api', '/api')
    .constant('toastr', toastr);

})();
