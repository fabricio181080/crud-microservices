package com.matera.crudmicroservices.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.matera.crudmicroservices.core.entities.Person;
import com.matera.crudmicroservices.service.PersonService;

import rx.Observable;

@Path("/person")
public class PersonRS {

	private final PersonService service;
	
	@Inject
	public PersonRS(PersonService service) {
		this.service = service;
	}
	
	@Path("/{id}")
	@GET
	@Produces({
		MediaType.APPLICATION_JSON
	})
	public Response findById(@PathParam("id") long id) {
		final Person person = service.findById(id).toBlocking().singleOrDefault(null);
		if (person == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(person).build();
	}
	
	@Path("/all")
	@GET
	@Produces({
		MediaType.APPLICATION_JSON
	})
	public Response all(@QueryParam("name") String name, @QueryParam("phoneNumber") String phoneNumber) {
		final Observable<Person> observable;
		
		if (name != null) {
			observable = service.findByName(name);
		} else {
			observable = service.findAll();
		}
		
		if (phoneNumber != null) {
			observable.filter((person) -> phoneNumber.equals(person.getPhoneNumber()));
		}
		
		final List<Person> persons = observable.toList().toBlocking().single();
		if (persons.isEmpty()) {
			Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(persons).build();
	}
	
}
