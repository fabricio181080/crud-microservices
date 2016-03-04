/*
 * Copyright 2016, Charter Communications, All rights reserved.
 */
package com.matera.crudmicroservices.api.rest;

import static com.matera.crudmicroservices.api.rest.ObservableUtil.checkNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.api.command.CreatePersonCommand;
import com.matera.crudmicroservices.core.entities.Person;
import com.netflix.niws.client.http.RestClient;

import rx.Observable;

/**
 * 
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
    public Observable<Person> createPerson(Person person) {

        return new CreatePersonCommand(mapper, restClient, person).observe().flatMap(checkNull());
    }

}
