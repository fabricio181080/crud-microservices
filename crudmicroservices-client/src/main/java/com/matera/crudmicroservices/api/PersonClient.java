package com.matera.crudmicroservices.api;

import com.matera.crudmicroservices.core.entities.Person;

import java.util.List;

import rx.Observable;

/**
 * Client to handle middle responses
 *
 * @author egzefer
 */
public interface PersonClient {

    /**
     * Retrieves all persons matching the given parameters.
     * 
     * @param name
     * @param phoneNumber
     * @return The domain list representation of the existent {@link Person}s
     */
    public Observable<List<Person>> all(String name, String phoneNumber);

    /**
     * Retrieves the Person that matches the given id.
     * 
     * @param id The {@link Person} id
     * @return The domain list representation of the existent {@link Person} or
     *         empty response if not found
     */
    public Observable<Person> byId(Long id);

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
     */
    public Observable<Void> removePerson(Long id);
}
