package com.matera.crudmicroservices.api;

import com.matera.crudmicroservices.core.domain.Person;

import rx.Observable;

/**
 * Client to handle middle responses
 *
 * @author egzefer
 */
public interface PersonClient {

    /**
     * Creates a person.
     * 
     * @param person The domain {@link Person} representation to be created
     * @return The domain representation of the created {@link Person}
     */
    public Observable<Person> createPerson(Person person);
}
