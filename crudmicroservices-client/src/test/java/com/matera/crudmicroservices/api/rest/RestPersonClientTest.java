package com.matera.crudmicroservices.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.crudmicroservices.core.entities.Person;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.niws.client.http.RestClient;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.junit.Assert;
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

    private ObjectMapper mapper;

    @InjectMocks
    RestPersonClient client;

    String input;

    @Before
    public void setUp() throws IOException {

        mapper = new ObjectMapper();
        client = new RestPersonClient(restClient, mapper);
        System.setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", "300000");

        try (InputStream inputStream = this.getClass().getResourceAsStream("/test-data/person.json")) {
            input = IOUtils.toString(inputStream);
        }
    }

    @Test
    public void createPerson() throws Exception {

        HttpResponse response = HttpResponseUtils.createResponse(HttpStatus.SC_OK, input);
        Mockito.when(restClient.execute(Mockito.any(HttpRequest.class))).thenReturn(response);

        Observable<Person> responsePerson = client.createPerson(mapper.readValue(input, Person.class));

        Person person = responsePerson.toBlocking().single();

        Assert.assertEquals(new Long(1), person.getId());
        Assert.assertEquals("Person Name", person.getName());
        Assert.assertEquals("12345", person.getPhoneNumber());
    }

    @Test
    public void updatePerson() throws Exception {

        HttpResponse response = HttpResponseUtils.createResponse(HttpStatus.SC_OK, input);
        Mockito.when(restClient.execute(Mockito.any(HttpRequest.class))).thenReturn(response);

        Observable<Person> responsePerson = client.updatePerson(1L, mapper.readValue(input, Person.class));

        Person person = responsePerson.toBlocking().single();

        Assert.assertEquals(new Long(1), person.getId());
        Assert.assertEquals("Person Name", person.getName());
        Assert.assertEquals("12345", person.getPhoneNumber());
    }

}
