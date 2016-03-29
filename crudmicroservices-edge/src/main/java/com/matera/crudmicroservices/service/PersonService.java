package com.matera.crudmicroservices.service;

import com.google.inject.Inject;
import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.core.entities.Person;
import com.matera.crudmicroservices.filter.PersonFilter;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

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
    public Observable<List<?>> getPersons(PersonFilter filter) {

        throw new NotImplementedException("Not yet");
    }

    /**
     * Returns a single person, filtering by id
     * 
     * @param id
     */
    public Observable<Object> getPerson(Long id) {

        throw new NotImplementedException("Not yet");
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
}
