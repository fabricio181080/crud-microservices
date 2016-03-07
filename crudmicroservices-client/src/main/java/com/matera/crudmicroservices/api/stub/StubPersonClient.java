/*
 * Copyright 2016, Charter Communications, All rights reserved.
 */
package com.matera.crudmicroservices.api.stub;

import com.google.inject.Inject;
import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.core.entities.Person;
import com.netflix.niws.client.http.RestClient;

import org.codehaus.jackson.map.ObjectMapper;

import rx.Observable;

/**
 * 
 *
 * @author egzefer
 */
public class StubPersonClient implements PersonClient {

    @Inject
    @SuppressWarnings("unused")
    public StubPersonClient(RestClient restClient, ObjectMapper mapper) {

    }

    /**
     * {@inheritDoc}
     */
    public Observable<Person> createPerson(Person person) {

        return Observable.just(person);
    }

}
