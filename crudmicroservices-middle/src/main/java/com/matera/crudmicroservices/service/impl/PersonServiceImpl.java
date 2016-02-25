package com.matera.crudmicroservices.service.impl;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;
import com.matera.crudmicroservices.core.convert.PersonConverter;
import com.matera.crudmicroservices.core.entities.Person;
import com.matera.crudmicroservices.service.PersonService;
import com.matera.crudmicroservices.store.PersonStore;

import rx.Observable;

public class PersonServiceImpl implements PersonService {

	private final PersonStore personStore;
	
	@Inject
	public PersonServiceImpl(PersonStore personStore) {
		this.personStore = personStore;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Observable<Person> findAll() {
		return personStore.findAll().map(PersonConverter::toEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Observable<Person> findById(long id) {
		return personStore.findById(id).map(PersonConverter::toEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Observable<Person> findByName(String name) {
		checkArgument(StringUtils.isNotBlank(name), "name mustn't be null or empty");
		return personStore.findByName(name).map(PersonConverter::toEntity);
	}
	
}
