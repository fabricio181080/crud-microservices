package com.matera.crudmicroservices.store.impl;



import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;
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
	
	private static final DynamicStringProperty COLUMN_FAMILY =
			DynamicPropertyFactory.getInstance().getStringProperty("crudmicroservices.cassandra.cf.person", "person");
	
	@Inject
	private final Provider<Session> session;
	
	public PersonStoreImpl(Provider<Session> session) {
		this.session = session;
	}
	
	@Override
	public Observable<Person> findAll() {

		final Select query = QueryBuilder.select().from(KEYSPACE.get(), COLUMN_FAMILY.get());
		
		return new SearchPerson(query, session.get()).observe();
	}

	@Override
	public Observable<Person> findById(long id) {
		
		final Select query = QueryBuilder.select().from(KEYSPACE.get(), COLUMN_FAMILY.get());
		query.where(eq("id", id));
		
		return new SearchPerson(query, session.get()).observe();
	}

	@Override
	public Observable<Person> findByName(String name) {
		
		final Select query = QueryBuilder.select().from(KEYSPACE.get(), COLUMN_FAMILY.get());
		query.where(eq("name", name));
		
		return new SearchPerson(query, session.get()).observe();
	}

	@Override
	public void save(Person person) {
		
		final Insert insert = 
				QueryBuilder.insertInto(KEYSPACE.get(), COLUMN_FAMILY.get())
					.value("id", person.getId())
					.value("name", person.getName())
					.value("phone_number", person.getPhoneNumber());
		
		session.get().execute(insert);
	}

	@Override
	public void update(Person person) {
		
		final Update update = QueryBuilder.update(KEYSPACE.get(), COLUMN_FAMILY.get());
		update.where(eq("id", person.getId()));

		session.get().execute(update);
	}

	@Override
	public void delete(Person person) {
		
		final Delete delete = QueryBuilder.delete().from(KEYSPACE.get(), COLUMN_FAMILY.get());
		delete.where(eq("id", person.getId())).and(eq("name", person.getName()));
		
		session.get().execute(delete);
	}

}
