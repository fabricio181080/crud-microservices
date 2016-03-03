package com.matera.crudmicroservices.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.crudmicroservices.core.domain.Person;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.niws.client.http.RestClient;

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

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    RestPersonClient client;

    @Before
    public void setUp() {

        client = new RestPersonClient(restClient, mapper);
    }

    @Test
    public void createPerson() throws Exception {

        String input = "";
        try (InputStream inputStream = this.getClass().getResourceAsStream("/test-data/person.json")) {
            input = IOUtils.toString(inputStream);
        }

        HttpResponse response = HttpResponseUtils.createResponse(HttpStatus.SC_OK, input);
        Mockito.when(restClient.execute(Mockito.any(HttpRequest.class))).thenReturn(response);

        Observable<Person> responsePerson = client.createPerson(Mockito.any(Person.class));

        Person person = responsePerson.toBlocking().single();

        Assert.assertEquals(new Long(1L), person.getId());
        Assert.assertEquals("Person Name", person.getName());
        Assert.assertEquals(new Long(12345), person.getPhoneNumber());
    }

}
