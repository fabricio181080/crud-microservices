package com.matera.crudmicroservices.edge.service.stub;

import java.util.List;

import com.google.common.collect.Lists;
import com.matera.crudmicroservices.core.domain.Person;
import com.matera.crudmicroservices.edge.rest.filter.PersonFilter;
import com.matera.crudmicroservices.edge.service.PersonService;

import rx.Observable;

/**
 * 
 * Stub class for PersonService
 * 
 * @author Igor K. Shiohara
 *
 */
public class PersonServiceStub implements PersonService{

	/**
	 * Create a Person stub
	 * @return {@link Observable} of {@link Person}
	 */
	public Observable<Person> create(final Person person) {
		return Observable.just(new Person.Builder()
									.withId(1L)
									.withName("Person name")
									.withPhoneNumber("98888-4444")
								.build());
	}

	@Override
	public Observable<List<Person>> getPersonsList(PersonFilter filter) {
		Person fakePerson = new Person.Builder()
				.withId(1L)
				.withName("Jose")
				.withPhoneNumber("99999-4444")
			.build();
		return Observable.just(Lists.newArrayList(fakePerson));
	}

	@Override
	public Observable<Person> getPersons(Long id) {
		Person fakePerson = new Person.Builder()
				.withId(1L)
				.withName("Jose")
				.withPhoneNumber("99999-4444")
			.build();
		return Observable.just(fakePerson);
	}
	
}
