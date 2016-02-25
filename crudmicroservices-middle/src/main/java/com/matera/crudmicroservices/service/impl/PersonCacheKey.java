package com.matera.crudmicroservices.service.impl;

class PersonCacheKey {

	private PersonCacheKey() {
		super();
	}
	
	static String byId(long id) {
		return "person_id:" + id;
	}
	
	static String byName(String name) {
		return "person_name:" + name;
	}
	
}
