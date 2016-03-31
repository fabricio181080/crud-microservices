package com.matera.crudmicroservices.service;

import com.google.inject.Inject;
import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.core.entities.Person;
import com.matera.crudmicroservices.filter.PersonFilter;

import java.util.List;

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
     * @param id the id of the person being updated
     * @param person the data to update the person
     * @return an Observable
     *         {@link com.matera.crudmicroservices.core.entities.Person Person}
     */
    public Observable<Person> updatePerson(Long id, Person person) {

        return personClient.updatePerson(id, person);
    }
}
