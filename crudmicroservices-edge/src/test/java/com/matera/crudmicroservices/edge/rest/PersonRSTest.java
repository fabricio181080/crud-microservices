package com.matera.crudmicroservices.edge.rest;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.matera.crudmicroservices.core.domain.Person;
import com.matera.crudmicroservices.edge.rest.filter.PersonFilter;
import com.matera.crudmicroservices.edge.service.PersonService;

import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class PersonRSTest {
	
	@Mock
	private PersonService service;
	private PersonRS personRS;

	@Before
	public void init(){
		personRS = new PersonRS(service);
	}

	/**
	 * Test if {@link PersonRS#getPersonList(PersonFilter)} will call its service
	 * and 'open' the {@link Observable}.
	 */
	@Test
	public void testGetPersonsList() {
		Person fakePerson = new Person();
		PersonFilter filter = new PersonFilter();
		Observable<List<Person>> observable = Observable.just(Lists.newArrayList(fakePerson));
		
		Mockito.when(service.getPersonList(filter)).thenReturn(observable);
		
		Response response = personRS.getPersonList(filter);
		Assert.assertNotNull(response.getEntity());
		
		@SuppressWarnings("unchecked")
		List<Person> persons = (List<Person>) response.getEntity();
		Assert.assertEquals(1, persons.size());
		Assert.assertEquals(fakePerson, persons.get(0));
		
		Mockito.verify(service, Mockito.only()).getPersonList(filter);
	}
	
	/**
	 * Test if {@link PersonRS#getPerson(Long)} will call its service
	 * and 'open' the {@link Observable}.
	 */
	@Test
	public void testGetPerson(){
		Person fakePerson = new Person();
		Observable<Person> observable = Observable.just(fakePerson);
		
		Mockito.when(service.getPerson(1l)).thenReturn(observable);
		
		Response response = personRS.getPerson(1l);
		Assert.assertNotNull(response.getEntity());		
		Assert.assertEquals(fakePerson, response.getEntity());		
		
		Mockito.verify(service, Mockito.only()).getPerson(1l);
	}

	
	/**
	 * Test if {@link PersonRS#getPerson(Long)} will return 404
	 * if there is no person with this <code>id</code>
	 */
	@Test
	public void testGetPersonNotFound(){
		Observable<Person> observable = Observable.empty();
		
		Mockito.when(service.getPerson(1l)).thenReturn(observable);
		
		Response response = personRS.getPerson(1l);
		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());		
		
		Mockito.verify(service, Mockito.only()).getPerson(1l);
	}
	
	/**
	 * Test if {@link PersonRS#createPerson(Person)} will return a correct {@link Response}
	 */
	@Test
	public void testCreatePerson(){
		Person fromForm = new Person();
		Person fromMock = new Person();
		Observable<Person> observable = Observable.just(fromMock);
		
		Mockito.when(service.createPerson(fromForm)).thenReturn(observable);
		
		Response response = personRS.createPerson(fromForm);
		Person newPerson = (Person) response.getEntity();

		Assert.assertNotNull(response.getEntity());	
		Assert.assertEquals(Status.CREATED.getStatusCode(), response.getStatus());	
		Assert.assertEquals(fromMock, newPerson);			
		
		Mockito.verify(service, Mockito.only()).createPerson(fromForm);
	}
	
	/**
	 * Test if {@link PersonRS#updatePerson(Long, Person)} will return a correct {@link Response}
	 */
	@Test
	public void testUpdatePerson(){
		Person fromForm = new Person();
		Person fromMock = new Person();
		Observable<Person> observable = Observable.just(fromMock);
		
		Mockito.when(service.updatePerson(1l, fromForm)).thenReturn(observable);
		
		Response response = personRS.updatePerson(1l, fromForm);
		Person newPerson = (Person) response.getEntity();

		Assert.assertNotNull(response.getEntity());	
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());	
		Assert.assertEquals(fromMock, newPerson);	
		
		Mockito.verify(service, Mockito.only()).updatePerson(1l, fromForm);	
	}
	
}
