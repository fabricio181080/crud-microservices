package com.matera.crudmicroservices.queries;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Select;
import com.matera.crudmicroservices.core.domain.Person;
import com.netflix.hystrix.HystrixCommand;

public class SearchPerson extends HystrixCommand<Person> {

	private static final HystrixCommand.Setter SETTER = null; // TODO
	
	private final Select query;
	private final Session session;
	
	public SearchPerson(Select query, Session session) {
		super(SETTER);
		
		this.query = query;
		this.session = session;
	}

	@Override
	protected Person run() throws Exception {
		// TODO
		return null;
	}

}
