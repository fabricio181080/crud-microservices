package com.matera.crudmicroservices.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import rx.functions.Func1;

@Path("/person")
public class PersonRS {

    private final PersonService service;

    @Inject
    public PersonRS(PersonService service) {
        this.service = service;
    }

    @Path("/{id}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response findById(@PathParam("id") long id) {

        final Person person = service.findById(id).toBlocking().singleOrDefault(null);
        if (person == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(person).build();
    }

    @Path("/all")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response all(@QueryParam("name") String name, @QueryParam("phoneNumber") String phoneNumber) {

        final Observable<Person> observable;
        if (name != null) {
            observable = service.findByName(name);
        } else {
            observable = service.findAll();
        }

        final List<Person> persons = observable.filter(phoneNumber(phoneNumber)).toList().toBlocking().single();
        if (persons.isEmpty()) {
            Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(persons).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Person person) {

        Observable<Person> insert = service.insert(person);
        return Response.ok(insert.toBlocking().single()).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Person person) {

        Observable<Person> update = service.update(id, person);
        return Response.ok(update.toBlocking().single()).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {

        service.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    private Func1<Person, Boolean> phoneNumber(String phoneNumber) {

        return (person) -> {
            if (phoneNumber != null) {
                return phoneNumber.equals(person.getPhoneNumber());
            }
            return true;
        };
    }

}
