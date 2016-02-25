package com.matera.crudmicroservices.service;

import com.matera.crudmicroservices.core.entities.Person;

import rx.Observable;

public interface PersonService {

	/**
	 * 
	 * @return an {@link Observable} of {@link Person} with all persons
	 * found on store.
	 */
	Observable<Person> findAll(); 
	
	/**
	 * Search person using provided id
	 * 
	 * @param id
	 * @return an {@link Observable} of {@link Person} with a single value
	 */
	Observable<Person> findById(long id);
	
	/**
	 * Search person using provided name
	 * 
	 * @param name
	 * @return an {@link Observable} of {@link Person} with provided name
	 */
	Observable<Person> findByName(String name);
	
}
