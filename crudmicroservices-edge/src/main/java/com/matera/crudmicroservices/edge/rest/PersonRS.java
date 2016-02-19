package com.matera.crudmicroservices.edge.rest;

import java.util.List;
import java.util.NoSuchElementException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.matera.crudmicroservices.edge.rest.filter.PersonFilter;
import com.matera.crudmicroservices.edge.service.PersonService;
import com.sun.jersey.api.core.InjectParam;

/**
 * Handle the endpoints for Person
 * 
 * @author falci
 *
 */
@Path("/services/v1/persons")
public class PersonRS {

	private final PersonService service;

	/**
	 * Constructor with add dependecies
	 * 
	 * @param service
	 */
	@Inject
	public PersonRS(PersonService service) {
		this.service = service;
	}

	/**
	 * Returns a list of person. It can be filtered by <code>name</code>.
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	public Response getPersonsList(@InjectParam PersonFilter filter) {
		List<?> persons = service.getPersonsList(filter).toBlocking().single();
		
		return Response.ok(persons).build();
	}

	/**
	 * Returns a single person, filtering by <code>id</code>.
	 * Returns <code>404</code> if there is no person for this <code>id</code>.
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	public Response getPerson(@PathParam("id") Long id) {
		try{
			Object person = service.getPersons(id).toBlocking().single();
		
			return Response.ok(person).build();
			
		} catch(NoSuchElementException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
