package com.matera.crudmicroservices.api;

import com.matera.crudmicroservices.core.entities.Person;

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

    /**
     * Removes a person.
     * 
     * @param id The {@link Person} id to be removed.
     * @return 
     * 
     */
    Observable<Void> removePerson(Long id);
}
