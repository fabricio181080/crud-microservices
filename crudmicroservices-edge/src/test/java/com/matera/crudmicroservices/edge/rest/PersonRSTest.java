package com.matera.crudmicroservices.edge.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.matera.crudmicroservices.core.entities.Person;
import com.matera.crudmicroservices.edge.rest.filter.PersonFilter;
import com.matera.crudmicroservices.edge.service.PersonServiceImpl;

import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class PersonRSTest {
	
	@Mock
	private PersonServiceImpl service;
	
	private PersonRS personRS;

	@Before
	public void init(){
		personRS = new PersonRS(service);
	}
	
	/**
	 * Test if {@link PersonRS#getPersonsList(PersonFilter)} will call its service
	 * and 'open' the {@link Observable}.
	 */
	@Test
	public void testGetPersonsList() {
		Person fakePerson = createPerson();
		PersonFilter filter = new PersonFilter();
		Observable<List<Person>> observable = Observable.just(Lists.newArrayList(fakePerson));
		
		when(service.getPersonsList(filter)).thenReturn(observable);
		
		Response response = personRS.getPersonsList(filter);
		assertNotNull(response.getEntity());
		
		List<Person> persons = (List<Person>) response.getEntity();
		assertEquals(1, persons.size());
		assertEquals(fakePerson, persons.get(0));
		
		verify(service, Mockito.only()).getPersonsList(filter);
	}

	/**
	 * Test if {@link PersonRS#getPerson(Long)} will call its service
	 * and 'open' the {@link Observable}.
	 */
	@Test
	public void testGetPerson(){
		Person fakePerson = createPerson();
		Observable<Person> observable = Observable.just(fakePerson);
		
		when(service.getPersons(1l)).thenReturn(observable);
		
		Response response = personRS.getPerson(1l);
		assertNotNull(response.getEntity());		
		assertEquals(fakePerson, response.getEntity());		
	}

	
	/**
	 * Test if {@link PersonRS#getPerson(Long)} will return 404
	 * if there is no person with this <code>id</code>
	 */
	@Test
	public void testGetPersonNotFound(){
		Observable<Person> observable = Observable.empty();
		
		when(service.getPersons(1l)).thenReturn(observable);
		
		Response response = personRS.getPerson(1l);
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());	
		
	}

	/**
	 * Util method to create a Person with default values
	 * @return
	 */
	private Person createPerson() {
		Person p = new Person();
		p.setId(1L);
		p.setName("Jose");
		p.setPhoneNumber("98888-5555");
		return p;
	}
}
