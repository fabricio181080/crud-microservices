package com.matera.crudmicroservices.edge.service.stub;

import java.util.List;

import com.google.common.collect.Lists;
import com.matera.crudmicroservices.core.entities.Person;
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
		Person p = new Person();
		p.setId(1L);
		p.setName("Person name");
		p.setPhoneNumber("98888-4444");
		
		return Observable.just(p);
	}

	@Override
	public Observable<List<Person>> getPersonsList(PersonFilter filter) {
		Person p = new Person();
		p.setId(1L);
		p.setName("Jose");
		p.setPhoneNumber("98888-5555");
		return Observable.just(Lists.newArrayList(p));
	}

	@Override
	public Observable<Person> getPersons(Long id) {
		Person p = new Person();
		p.setId(1L);
		p.setName("Jose");
		p.setPhoneNumber("98888-5555");
		return Observable.just(p);
	}
	
}
