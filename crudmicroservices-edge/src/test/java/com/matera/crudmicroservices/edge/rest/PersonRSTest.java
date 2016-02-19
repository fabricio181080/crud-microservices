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
	 * Test if {@link PersonRS#getPersonsList(PersonFilter)} will call its service
	 * and 'open' the {@link Observable}.
	 */
	@Test
	public void testGetPersonsList() {
		Object fakePerson = new Object();
		PersonFilter filter = new PersonFilter();
		Observable<List<?>> observable = Observable.just(Lists.newArrayList(fakePerson));
		
		Mockito.when(service.getPersonsList(filter)).thenReturn(observable);
		
		Response response = personRS.getPersonsList(filter);
		Assert.assertNotNull(response.getEntity());
		
		List<?> persons = (List<?>) response.getEntity();
		Assert.assertEquals(1, persons.size());
		Assert.assertEquals(fakePerson, persons.get(0));
		
		Mockito.verify(service, Mockito.only()).getPersonsList(filter);
	}
	
	/**
	 * Test if {@link PersonRS#getPerson(Long)} will call its service
	 * and 'open' the {@link Observable}.
	 */
	@Test
	public void testGetPerson(){
		Object fakePerson = new Object();
		Observable<Object> observable = Observable.just(fakePerson);
		
		Mockito.when(service.getPersons(1l)).thenReturn(observable);
		
		Response response = personRS.getPerson(1l);
		Assert.assertNotNull(response.getEntity());		
		Assert.assertEquals(fakePerson, response.getEntity());		
	}

	
	/**
	 * Test if {@link PersonRS#getPerson(Long)} will return 404
	 * if there is no person with this <code>id</code>
	 */
	@Test
	public void testGetPersonNotFound(){
		Observable<Object> observable = Observable.empty();
		
		Mockito.when(service.getPersons(1l)).thenReturn(observable);
		
		Response response = personRS.getPerson(1l);
		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());	
		
	}

}
