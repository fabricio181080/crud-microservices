package com.matera.crudmicroservices.rest;

import java.util.List;
import java.util.NoSuchElementException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.matera.crudmicroservices.core.entities.Person;
import com.matera.crudmicroservices.filter.PersonFilter;
import com.matera.crudmicroservices.service.PersonService;
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
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPersons(@InjectParam PersonFilter filter) {

        List<Person> peopleList = service.getPersons(filter).toBlocking().single();
        return Response.ok(peopleList).build();
    }

    /**
     * Returns a single person, filtering by <code>id</code>. Returns
     * <code>404</code> if there is no person for this <code>id</code>.
     * 
     * @param id
     */
    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPerson(@PathParam("id") Long id) {

        try {
            Person person = service.getPerson(id).toBlocking().single();
            return Response.ok(person).build();
        } catch (NoSuchElementException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createPerson(Person person) {

        return Response.status(Status.CREATED).entity(service.createPerson(person).toBlocking().single()).build();
    }

    /**
     * Updates a {@link Person}
     * 
     * @param id
     * @param person
     * @return Response
     */
    @PUT
    @Path("/{id}")
    public Response updatePerson(@PathParam("id") Long id, Person person) {

        Person newPerson = service.updatePerson(id, person).toBlocking().single();
        return Response.ok(newPerson).build();
    }
    
    /**
     * Remove a {@link Person}
     * 
     * @param id
     * @param person
     * @return Response
     */
    @DELETE
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response removePerson(@PathParam("id") Long id, Person person) {

        service.removePerson(id, person).toBlocking().single();
        return Response.status(Status.NO_CONTENT).build();
    }
}
