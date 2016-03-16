package com.matera.crudmicroservices.api.stub;

import com.google.inject.Inject;
import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.core.entities.Person;
import com.netflix.niws.client.http.RestClient;

import java.util.ArrayList;
import java.util.List;

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
    public Observable<List<Person>> all(String name, String phoneNumber) {

        return Observable.just(new ArrayList<>());
    }

    /**
     * {@inheritDoc}
     */
    public Observable<Person> byId(Long id) {

        final Person person = new Person();
        person.setId(id);
        return Observable.just(person);
    }

    /**
     * {@inheritDoc}
     */
    public Observable<Person> createPerson(Person person) {

        return Observable.just(person);
    }

}
