package com.matera.crudmicroservices.api.rest;

import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.api.command.CreatePersonCommand;
import com.matera.crudmicroservices.api.command.FindAllPersonsCommand;
import com.matera.crudmicroservices.api.command.FindPersonByIdCommand;
import com.matera.crudmicroservices.core.entities.Person;
import com.netflix.niws.client.http.RestClient;

import java.util.List;

import rx.Observable;

/**
 * Client Rest implementation to handle middle responses
 *
 * @author egzefer
 */
public class RestPersonClient implements PersonClient {

    private RestClient restClient;
    private ObjectMapper mapper;

    @Inject
    public RestPersonClient(RestClient restClient, com.fasterxml.jackson.databind.ObjectMapper mapper) {

        this.restClient = restClient;
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    public Observable<List<Person>> all(String name, String phoneNumber) {

        return new FindAllPersonsCommand(mapper, restClient, name, phoneNumber).observe();
    }

    /**
     * {@inheritDoc}
     */
    public Observable<Person> byId(Long id) {

        checkNotNull(id, "The given id musn't be null");
        return new FindPersonByIdCommand(mapper, restClient, id).observe();
    }

    /**
     * {@inheritDoc}
     */
    public Observable<Person> createPerson(Person person) {

        checkNotNull(person, "Person musn't be null");
        return new CreatePersonCommand(mapper, restClient, person).observe();
    }

}
