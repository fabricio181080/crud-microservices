package com.matera.crudmicroservices.edge.service;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import com.google.inject.Inject;
import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.core.domain.Person;
import com.matera.crudmicroservices.edge.rest.filter.PersonFilter;

import rx.Observable;

/**
 * Service to handle client responses
 * 
 * @author falci
 *
 */
public class PersonService {
	
	PersonClient client;
	
	/**
	 * Constructor with all arguments
	 * 
	 */
	@Inject
	public PersonService(PersonClient client) {
		
		this.client = client;
	}

	/**
	 * Returns a list of person. It can be filtered by name
	 * 
	 * @param filter
	 * @return
	 */
	public Observable<List<Person>> getPersonList(PersonFilter filter) {
		throw new NotImplementedException("Not yet");
	}

	/**
	 * Returns a single person, filtering by id
	 * 
	 * @param id
	 * @return
	 */
	public Observable<Person> getPerson(Long id) {
		throw new NotImplementedException("Not yet");
	}

	/**
	 * Creates a new {@link Person}
	 * 
	 * @param person
	 * @return
	 */
	public Observable<Person> createPerson(Person person) {
//		return client.createPerson(person);		

		throw new NotImplementedException("Not yet");
	}

	/**
	 * Updates a {@link Person}
	 * 
	 * @param id
	 * @param person
	 * @return
	 */
	public Observable<Person> updatePerson(Long id, Person person) {
		throw new NotImplementedException("Not yet");
	}

}
