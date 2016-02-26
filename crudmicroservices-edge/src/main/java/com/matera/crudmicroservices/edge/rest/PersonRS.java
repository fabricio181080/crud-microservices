package com.matera.crudmicroservices.edge.rest;

import java.util.List;
import java.util.NoSuchElementException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.matera.crudmicroservices.core.domain.Person;
import com.matera.crudmicroservices.edge.rest.filter.PersonFilter;
import com.matera.crudmicroservices.edge.service.PersonService;
import com.sun.jersey.api.core.InjectParam;

import rx.Observable;

/**
 * Handle the endpoints for Person
 * 
 * @author falci
 * @author Igor K. Shiohara
 *
 */
@Path("/services/v1/persons")
public class PersonRS {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonRS.class);

	private final PersonService service;

	/**
	 * Constructor with add dependencies
	 * 
	 * @param service
	 */
	@Inject
	public PersonRS(PersonService service) {
		this.service = service;
	}
	
	/**
	 * REST Service to create a Person
	 * @param person
	 * @return Response with a created Person 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(final Person person) {
		Observable<Person> result = service.create(person)
				.doOnCompleted(() -> LOGGER.info("Person created succesfully."))
				.doOnError((error) -> LOGGER.info("Failed to create a Person:" + error));
		return Response.ok(result.toBlocking().first()).build();
	}

	/**
	 * Returns a list of person. It can be filtered by <code>name</code>.
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	public Response getPersonsList(@InjectParam PersonFilter filter) {
		List<Person> persons = service.getPersonsList(filter).toBlocking().single();
		
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
			Person person = service.getPersons(id).toBlocking().single();
		
			return Response.ok(person).build();
			
		} catch(NoSuchElementException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
