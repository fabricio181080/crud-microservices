package com.matera.crudmicroservices.api.rest;

import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.api.command.FindAllPersonsCommand;
import com.matera.crudmicroservices.api.command.FindPersonByIdCommand;
import com.matera.crudmicroservices.api.command.PersonCreateCommand;
import com.matera.crudmicroservices.api.command.PersonDeleteCommand;
import com.matera.crudmicroservices.api.command.PersonUpdateCommand;
import com.matera.crudmicroservices.core.config.CrudMicroservices;
import com.matera.crudmicroservices.core.entities.Person;
import com.netflix.niws.client.http.RestClient;

import java.util.List;

import rx.Observable;

/**
 * Client Rest implementation to handle middle responses
 *
 * @author egzefer
 * @author geiser
 */
public class RestPersonClient implements PersonClient {

    private RestClient restClient;
    private ObjectMapper mapper;

    @Inject
    public RestPersonClient(@Named("ValidationRestClient") RestClient restClient, @CrudMicroservices ObjectMapper mapper) {

        this.restClient = restClient;
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<List<Person>> all(String name, String phoneNumber) {

        return new FindAllPersonsCommand(mapper, restClient, name, phoneNumber).observe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<Person> byId(Long id) {

        checkNotNull(id, "The given id musn't be null");
        return new FindPersonByIdCommand(mapper, restClient, id).observe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<Person> createPerson(Person person) {

        checkNotNull(person, "Person musn't be null");
        return new PersonCreateCommand(mapper, restClient, person).observe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<Person> updatePerson(Long id, Person person) {

        return new PersonUpdateCommand(mapper, restClient, id, person).observe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<Void> removePerson(Long id) {

        checkNotNull(id, "Person id must not be null");
        return new PersonDeleteCommand(restClient, id).observe();
    }
}
