(function() {
  'use strict';

  angular
    .module('crudmicroservicesUi')
    .run(personsMock);

    function personsMock($httpBackend, personsConfig) {

      var nextId = 1;
      var assets = [
        {
            id : nextId++,
            name : 'Pedro',
            phoneNumber: '(44) 1111-1111'
        },
        {
            id : nextId++,
            name : 'Joao',
            phoneNumber: '(44) 2222-1111'
        }
      ];

      $httpBackend.whenGET(personsConfig.mock.list).respond({
        totalAssets : assets.length,
        assets : assets
      });

      $httpBackend.whenPOST(personsConfig.mock.list).respond(function(method, url, data) {
        var person = JSON.fromJson(data);
        person.id = nextId++;
        assets.push(person);
        return [201, person];
      });

      $httpBackend.whenGET(personsConfig.mock.edit).respond(function(method, url) {
        var idIndex = url.lastIndexOf("/");
        var id = url.substring(idIndex + 1 , url.length);

        var person = assets.find(function(p) {
          return p.id === parseInt(id);
        });

        if (person) {
          return [200, person];
        }

        return [404];
      });

      $httpBackend.whenPUT(personsConfig.mock.edit).respond(function(method, url, data) {
        var idIndex = url.lastIndexOf("/");
        var id = url.substring(idIndex + 1 , url.length);

        var person = assets.find(function(p) {
          return p.id === parseInt(id);
        });

        if (person) {
          var newPerson = JSON.fromJson(data);
          angular.copy(newPerson, person);

          return [200, person];
        }

        return [404];
      });
    }

})();
