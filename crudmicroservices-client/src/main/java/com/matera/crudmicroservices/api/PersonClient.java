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
     * Updates a person.
     * 
     * @param id The {@link Person} id to be updated
     * @param person The {@link Person} with the updated data
     * @return The domain representation of the updated {@link Person}
     */
    public Observable<Person> updatePerson(Long id, Person person);

    /**
     * Removes a person.
     * 
     * @param id The {@link Person} id to be removed.
     * @return 
     * 
     */
    Observable<Void> removePerson(Long id);
}
