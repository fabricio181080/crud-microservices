package com.matera.crudmicroservices.store.impl;



import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.matera.crudmicroservices.core.domain.Person;
import com.matera.crudmicroservices.queries.SearchPerson;
import com.matera.crudmicroservices.store.PersonStore;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

import rx.Observable;

public class PersonStoreImpl implements PersonStore {

	static final Logger logger = LoggerFactory.getLogger(PersonStoreImpl.class);
	
	
	private static final DynamicStringProperty KEYSPACE = 
			DynamicPropertyFactory.getInstance().getStringProperty("crudmicroservices.cassandra.keyspace", "crudmicroservices");
	
	private static final DynamicStringProperty PERSON_COLUMN_FAMILY =
			DynamicPropertyFactory.getInstance().getStringProperty("crudmicroservices.cassandra.cf.person", "person");
	
	private static final DynamicStringProperty PERSON_BY_NAME_COLUMN_FAMILY =
			DynamicPropertyFactory.getInstance().getStringProperty("crudmicroservices.cassandra.cf.personbyname", "person_by_name");
	

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String PHONE_NUMBER = "phone_number";
	
	@Inject
	private final Provider<Session> session;
	
	public PersonStoreImpl(Provider<Session> session) {
		this.session = session;
	}
	
	@Override
	public Observable<Person> findAll() {
		
		logger.info("Querying for all persons");
		final Select query = QueryBuilder.select().from(KEYSPACE.get(), PERSON_COLUMN_FAMILY.get());
		
		return new SearchPerson(query, session.get()).observe().flatMap((persons) -> Observable.from(persons));
	}

	@Override
	public Observable<Person> findById(long id) {
		
		logger.info("Querying for person with id = {}", id);
		final Select query = QueryBuilder.select().from(KEYSPACE.get(), PERSON_COLUMN_FAMILY.get());
		query.where(eq(ID, id));
		
		return new SearchPerson(query, session.get()).observe().flatMap((persons) -> Observable.from(persons));
	}

	@Override
	public Observable<Person> findByName(String name) {
		
		logger.info("Querying for person with name = {}", name);
		final Select query = QueryBuilder.select().from(KEYSPACE.get(), PERSON_BY_NAME_COLUMN_FAMILY.get());
		query.where(eq(NAME, name));
		query.allowFiltering();
		
		return new SearchPerson(query, session.get()).observe().flatMap((persons) -> Observable.from(persons));
	}

	@Override
	public void save(Person person) {
		
		logger.info("Saving {} on CF = {}", person, PERSON_COLUMN_FAMILY.get());
		final Insert insertPerson = 
				QueryBuilder.insertInto(KEYSPACE.get(), PERSON_COLUMN_FAMILY.get())
					.value(ID, person.getId())
					.value(NAME, person.getName())
					.value(PHONE_NUMBER, person.getPhoneNumber());
		
		session.get().execute(insertPerson);
		
		logger.info("Saving {} on CF = {}", person, PERSON_BY_NAME_COLUMN_FAMILY.get());
		final Insert insertPersonByName = 
				QueryBuilder.insertInto(KEYSPACE.get(), PERSON_BY_NAME_COLUMN_FAMILY.get())
					.value(ID, person.getId())
					.value(NAME, person.getName())
					.value(PHONE_NUMBER, person.getPhoneNumber());
		
		session.get().execute(insertPersonByName);
	}

	@Override
	public void update(Person person) {
		
		logger.info("Updating {}", person);
		delete(person);
		save(person);
	}

	@Override
	public void delete(Person person) {
		
		logger.info("Deleting {} from CF = {}", person, PERSON_COLUMN_FAMILY.get());
		final Delete delete = QueryBuilder.delete().from(KEYSPACE.get(), PERSON_COLUMN_FAMILY.get());
		delete.where(eq(ID, person.getId()));

		session.get().execute(delete);
		
		logger.info("Deleting {} from CF = {}", person, PERSON_BY_NAME_COLUMN_FAMILY.get());
		final Delete deleteByName = QueryBuilder.delete().from(KEYSPACE.get(), PERSON_BY_NAME_COLUMN_FAMILY.get());
		deleteByName.where(eq(ID, person.getId())).and(eq(NAME, person.getName()));
		
		session.get().execute(deleteByName);
	}

}
