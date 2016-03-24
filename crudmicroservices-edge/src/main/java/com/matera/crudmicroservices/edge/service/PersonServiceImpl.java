package com.matera.crudmicroservices.edge.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.core.entities.Person;
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
	
	private PersonClient personClient;

	/**
 	 * 
	 * Constructor with all arguments
	 * 
	 * @param personClient
	 */
	@Inject
	public PersonServiceImpl(PersonClient personClient) {
		this.personClient = personClient;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Observable<Person> create(final Person person) {
		LOGGER.info("Creating a person " + person);
		return personClient.createPerson(person);
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
