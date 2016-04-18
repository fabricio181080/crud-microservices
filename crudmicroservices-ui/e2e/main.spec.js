'use strict';

describe('The main view', function () {
  var page;

  beforeEach(function () {
    browser.get('/index.html');
    page = require('./main.po');
  });

  it('should include the project name as first link', function() {

    expect(page.navLinks.first().getText()).toContain('Microservices UI');

  });

  it('should include the project Github link as last link', function() {

    expect(page.navLinks.last().getText()).toContain('Github');

  });

});
