package com.matera.crudmicroservices.queries;

import java.util.Set;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Select;
import com.google.common.collect.Sets;
import com.matera.crudmicroservices.core.domain.Person;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

public class SearchPerson extends HystrixCommand<Set<Person>> {

	private static final HystrixCommand.Setter SETTER = Setter.withGroupKey(
	        HystrixCommandGroupKey.Factory.asKey("SearchPerson")).andCommandKey(
	        HystrixCommandKey.Factory.asKey(SearchPerson.class.getName()));
	
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String PHONE_NUMBER = "phone_number";
	
	private final Select query;
	private final Session session;
	
	public SearchPerson(Select query, Session session) {
		super(SETTER);
		
		this.query = query;
		this.session = session;
	}

	@Override
	protected Set<Person> run() throws Exception {
		
		final ResultSet resultset = session.execute(query);
		final Set<Person> result = Sets.newHashSet();
		resultset.forEach((row) -> {
			result.add(toEntity(row));
		});
		return result;
	}
	
	private Person toEntity(Row row) {
		
		return Person.builder()
				.withId(row.getLong(ID))
				.withName(row.getString(NAME))
				.withPhoneNumber(row.getString(PHONE_NUMBER))
				.build();
	}

}
