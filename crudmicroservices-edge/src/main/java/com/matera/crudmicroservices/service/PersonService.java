package com.matera.crudmicroservices.service;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import com.google.inject.Inject;
import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.core.entities.Person;
import com.matera.crudmicroservices.filter.PersonFilter;

import rx.Observable;

/**
 * Service to handle client responses
 * 
 * @author falci
 */
public class PersonService {

    private final PersonClient personClient;

    /**
     * Constructor with all arguments
     */
    @Inject
    public PersonService(PersonClient personClient) {

        this.personClient = personClient;
    }

    /**
     * Returns a list of person. It can be filtered by name
     * 
     * @param filter
     */
    public Observable<List<Person>> getPersons(PersonFilter filter) {

        return personClient.all(filter.getName(), filter.getPhoneNumber());
    }

    /**
     * Returns a single person, filtering by id
     * 
     * @param id
     */
    public Observable<Person> getPerson(Long id) {

        return personClient.byId(id);
    }

    /**
     * @param person
     * @return Observable&lt;
     *         {@link com.matera.crudmicroservices.core.entities.Person Person}
     *         &gt;
     */
    public Observable<Person> createPerson(Person person) {

        return personClient.createPerson(person);
    }

    /**
     * Updates a {@link Person}
     * 
     * @param id
     * @param person
     * @return
     */
    public Observable<Person> updatePerson(Long id, Person person) {

        throw new NotImplementedException("Not yet");
    }
    
    /**
     * Remove a {@link Person}
     * 
     * @param id
     * @param person
     * @return
     */
    public Observable<Void> removePerson(Long id, Person person) {

        return personClient.removePerson(id);
    }
}
