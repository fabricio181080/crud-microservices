package com.matera.crudmicroservices.api.rest;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.crudmicroservices.core.entities.Person;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.niws.client.http.RestClient;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;

/**
 *
 *
 * @author egzefer
 */
@RunWith(MockitoJUnitRunner.class)
public class RestPersonClientTest {

    @Mock
    private RestClient restClient;

    @InjectMocks
    RestPersonClient client = new RestPersonClient(restClient, new ObjectMapper());

    @Before
    public void setUp() {

        System.setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", "300000");
    }

    @Test
    public void createPerson() throws Exception {

        Person stubPerson = createStubPerson();

        HttpResponse response = HttpResponseUtils.createResponse(HttpStatus.SC_OK, stubPerson);
        Mockito.when(restClient.execute(Mockito.any(HttpRequest.class))).thenReturn(response);

        Observable<Person> responsePerson = client.createPerson(stubPerson);

        Person person = responsePerson.toBlocking().single();

        assertEquals(stubPerson.getId(), person.getId());
        assertEquals(stubPerson.getName(), person.getName());
        assertEquals(stubPerson.getPhoneNumber(), person.getPhoneNumber());
    }

    @Test
    public void updatePerson() throws Exception {

        Person stubPerson = createStubPerson();

        HttpResponse response = HttpResponseUtils.createResponse(HttpStatus.SC_OK, stubPerson);
        Mockito.when(restClient.execute(Mockito.any(HttpRequest.class))).thenReturn(response);

        Observable<Person> responsePerson = client.updatePerson(1L, stubPerson);

        Person person = responsePerson.toBlocking().single();

        assertEquals(stubPerson.getId(), person.getId());
        assertEquals(stubPerson.getName(), person.getName());
        assertEquals(stubPerson.getPhoneNumber(), person.getPhoneNumber());
    }

    private Person createStubPerson() {

        Person person = new Person();
        person.setId(1L);
        person.setName("Stub Person");
        person.setPhoneNumber("12345");
        return person;
    }

}
