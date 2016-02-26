package com.matera.crudmicroservices.edge.service;

import java.util.List;

import com.matera.crudmicroservices.core.domain.Person;
import com.matera.crudmicroservices.edge.rest.filter.PersonFilter;

import rx.Observable;

/**
 * @author Igor K. Shiohara
 */
public interface PersonService {

	/**
	 * Create a Person
	 * @return
	 */
	Observable<Person> create(Person person);

	/**
	 * Returns a list of person. It can be filtered by name
	 * 
	 * @param filter
	 * @return
	 */
	Observable<List<Person>> getPersonsList(PersonFilter filter);

	/**
	 * Returns a single person, filtering by id
	 * 
	 * @param id
	 * @return
	 */
	Observable<Person> getPersons(Long id);

}