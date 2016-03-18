/*
 * Copyright 2016, Charter Communications, All rights reserved.
 */
package com.matera.crudmicroservices.api.stub;

import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.core.entities.Person;

import rx.Observable;

/**
 * 
 *
 * @author egzefer
 */
public class StubPersonClient implements PersonClient {

    /**
     * {@inheritDoc}
     */
    public Observable<Person> createPerson(Person person) {

        return Observable.just(person);
    }

    @Override
    public Observable<Void> removePerson(Long id) {

        return Observable.just(null);
    }

}
