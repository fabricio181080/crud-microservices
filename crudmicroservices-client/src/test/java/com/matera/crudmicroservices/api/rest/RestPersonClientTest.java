package com.matera.crudmicroservices.api.rest;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.crudmicroservices.core.entities.Person;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpRequest.Verb;
import com.netflix.client.http.HttpResponse;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.niws.client.http.RestClient;

import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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

    private ObjectMapper mapper;

    @InjectMocks
    RestPersonClient client;

    @Before
    public void setUp() {

        mapper = new ObjectMapper();
        client = new RestPersonClient(restClient, mapper);
        System.setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", "300000");
    }

    @Test
    public void createPerson() throws Exception {

        Person stubPerson = createStubPerson();

        HttpResponse response = HttpResponseUtils.createResponse(HttpStatus.SC_OK, stubPerson);
        Mockito.when(restClient.execute(Mockito.any(HttpRequest.class))).thenReturn(response);

        Observable<Person> responsePerson = client.createPerson(stubPerson);

        Person person = responsePerson.toBlocking().single();

        assertEquals(new Long(1), person.getId());
        assertEquals("Stub Person", person.getName());
        assertEquals("12345", person.getPhoneNumber());
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

    @Test
    public void testDelete() throws Exception {

        Mockito.when(restClient.execute(Mockito.any(HttpRequest.class)))
            .thenReturn(HttpResponseUtils.createResponse(HttpStatus.SC_NO_CONTENT, null));

        client.removePerson(1l).toBlocking().single();

        ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        Mockito.verify(restClient).execute(requestCaptor.capture());

        assertEquals("crudmicroservicesmiddle/person/1", requestCaptor.getValue().getUri().toString());
        assertEquals(Verb.DELETE, requestCaptor.getValue().getVerb());
    }

    @Test(expected = HystrixRuntimeException.class)
    public void testWithErrorResponse() throws Exception {

        Mockito.when(restClient.execute(Mockito.any(HttpRequest.class)))
            .thenReturn(HttpResponseUtils.createResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, null, false));

        client.removePerson(1l).toBlocking().single();
    }

    @Test
    public void findAllPersons() throws Exception {

        Person stubPerson = createStubPerson();

        HttpResponse response = HttpResponseUtils.createResponse(HttpStatus.SC_OK, Arrays.asList(stubPerson));

        Mockito.when(restClient.execute(Mockito.any(HttpRequest.class))).thenReturn(response);

        Observable<List<Person>> responsePerson = client.all(null, null);

        Person person = responsePerson.toBlocking().single().get(0);

        assertEquals(new Long(1), person.getId());
        assertEquals("Stub Person", person.getName());
        assertEquals("12345", person.getPhoneNumber());
    }

    private Person createStubPerson() {

        Person person = new Person();
        person.setId(1L);
        person.setName("Stub Person");
        person.setPhoneNumber("12345");
        return person;
    }

}
