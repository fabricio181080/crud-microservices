(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise(function($injector){
      $injector.get('$state')
        .go('app.home');
    });

    $stateProvider
      .state('app', {
        url: '/app',
        templateUrl: 'app/views/main/main.html',
        abstract: true
      })
      .state('app.home', {
        url: '/home',
        templateUrl: 'app/views/main/home.html',
        controller: 'HomeController',
        controllerAs: 'home'
      })

      /*********************
        Persons
      /********************/
      .state('app.persons', {
        url: '/persons',
        templateUrl: 'app/views/persons/list.html',
        controller: 'PersonsController',
        controllerAs: 'vm'
      })
      .state('app.persons.add', {
        url: '/add',
        views: {
          '@app': {
            templateUrl: 'app/views/persons/form.html',
            controller: 'PersonAddController',
            controllerAs: 'vm'
          }
        }
      })
      .state('app.persons.edit', {
        url: '/:id',
        views: {
          '@app': {
            templateUrl: 'app/views/persons/form.html',
            controller: 'PersonEditController',
            controllerAs: 'vm'
          }
        }
      });
  }

})();
