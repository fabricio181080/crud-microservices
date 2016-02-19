package com.matera.crudmicroservices.edge.service;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import com.google.inject.Inject;
import com.matera.crudmicroservices.edge.rest.filter.PersonFilter;

import rx.Observable;

/**
 * Service to handle client responses
 * 
 * @author falci
 *
 */
public class PersonService {
	
	/**
	 * Constructor with all arguments
	 * 
	 */
	@Inject
	public PersonService() {
	}

	/**
	 * Returns a list of person. It can be filtered by name
	 * 
	 * @param filter
	 * @return
	 */
	public Observable<List<?>> getPersonsList(PersonFilter filter) {
		throw new NotImplementedException("Not yet");
	}

	/**
	 * Returns a single person, filtering by id
	 * 
	 * @param id
	 * @return
	 */
	public Observable<Object> getPersons(Long id) {
		throw new NotImplementedException("Not yet");
	}

}
