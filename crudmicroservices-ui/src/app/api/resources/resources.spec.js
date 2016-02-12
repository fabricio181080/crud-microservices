(function() {
  'use strict';

  describe('resourceAPI', function(){
    var $resourceSpy, resourceAPI;

    beforeEach(function () {
      $resourceSpy = jasmine.createSpy();
    });
    beforeEach(module('crudmicroservicesUi'));
    beforeEach(module(function ($provide) {
      $provide.value('$resource', $resourceSpy);
    }));
    beforeEach(inject(function(_resourceAPI_) {
      resourceAPI = _resourceAPI_;
    }));

    it('should add update as a PUT method', function() {
      resourceAPI('URL');

      expect($resourceSpy).toHaveBeenCalled();
      expect($resourceSpy.calls.mostRecent().args[2].update).toBeDefined();
      expect($resourceSpy.calls.mostRecent().args[2].update.method).toBe('PUT');
    });
  });
})();
