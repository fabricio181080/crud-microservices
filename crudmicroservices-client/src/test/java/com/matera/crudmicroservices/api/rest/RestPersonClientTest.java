package com.matera.crudmicroservices.api.rest;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.crudmicroservices.core.entities.Person;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpRequest.Verb;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.client.http.HttpResponse;
import com.netflix.niws.client.http.RestClient;

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

        String input = "";
        try (InputStream inputStream = this.getClass().getResourceAsStream("/test-data/person.json")) {
            input = IOUtils.toString(inputStream);
        }

        HttpResponse response = HttpResponseUtils.createResponse(HttpStatus.SC_OK, input);
        Mockito.when(restClient.execute(Mockito.any(HttpRequest.class))).thenReturn(response);

        Observable<Person> responsePerson = client.createPerson(mapper.readValue(input, Person.class));

        Person person = responsePerson.toBlocking().single();

        Assert.assertEquals(new Long(1), person.getId());
        Assert.assertEquals("Person Name", person.getName());
        Assert.assertEquals("12345", person.getPhoneNumber());
    }

    @Test
    public void testDelete() throws Exception {

        Mockito.when(restClient.execute(Mockito.any(HttpRequest.class)))
            .thenReturn(HttpResponseUtils.createResponse(HttpStatus.SC_NO_CONTENT, null));

        client.removePerson(1l).toBlocking().single();

        ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        Mockito.verify(restClient).execute(requestCaptor.capture());

        Assert.assertEquals("crudmicroservicesmiddle/person/1", requestCaptor.getValue().getUri().toString());
        Assert.assertEquals(Verb.DELETE, requestCaptor.getValue().getVerb());
    }

    @Test(expected = HystrixRuntimeException.class)
    public void testWithErrorResponse() throws Exception {

        Mockito.when(restClient.execute(Mockito.any(HttpRequest.class)))
            .thenReturn(HttpResponseUtils.createResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, null, false));

        client.removePerson(1l).toBlocking().single();
    }

}
