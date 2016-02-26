package com.matera.crudmicroservices.edge.service;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.matera.crudmicroservices.core.domain.Person;
import com.matera.crudmicroservices.edge.rest.filter.PersonFilter;

import rx.Observable;

/**
 * Service to handle client responses
 * 
 * @author falci
 * @author Igor K. Shiohara
 *
 */
public class PersonServiceImpl implements PersonService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);
	
	/**
	 * Constructor with all arguments
	 * 
	 */
	@Inject
	public PersonServiceImpl() {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Observable<Person> create(final Person person) {
		//TODO Call a client layer
		LOGGER.info("Creating a person " + person);
		throw new NotImplementedException("Not yet");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Observable<List<Person>> getPersonsList(PersonFilter filter) {
		throw new NotImplementedException("Not yet");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Observable<Person> getPersons(Long id) {
		throw new NotImplementedException("Not yet");
	}

}
