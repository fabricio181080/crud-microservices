package com.matera.crudmicroservices.rest;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.inject.util.Providers;
import com.matera.crudmicroservices.core.convert.PersonConverter;
import com.matera.crudmicroservices.core.domain.Person;
import com.matera.crudmicroservices.store.PersonStore;
import com.matera.crudmicroservices.store.impl.PersonStoreCassandra;

public class PersonRSIT {

    private static final String URL = "http://localhost:9080/crudmicroservicesmiddle";

    @Before
    public void beforeFixture() {

        Session session = Cluster.builder().addContactPoint("127.0.0.1").build().connect("crudmicroservices");

        session.execute("TRUNCATE person;");
        session.execute("TRUNCATE person_by_name;");

        PersonStore store = new PersonStoreCassandra(Providers.of(session));

        store.save(Person.builder().withId(1L).withName("Andre Grant").withPhoneNumber("202-555-0166").build());
        store.save(Person.builder().withId(2L).withName("Rachael Mccormick").withPhoneNumber("202-555-0187").build());
        store.save(Person.builder().withId(3L).withName("Willie Barrett").withPhoneNumber("202-555-0155").build());
        store.save(Person.builder().withId(4L).withName("Marcus Martinez").withPhoneNumber("202-555-0187").build());
        store.save(Person.builder().withId(5L).withName("Andre Grant").withPhoneNumber("202-555-0155").build());
    }

    @Test
    public void byId() throws Exception {

        final String uri = URL + "/person/3";
        HttpResponse response = doGET(uri);

        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatusLine().getStatusCode());

        com.matera.crudmicroservices.core.entities.Person person =
            Utils.fromJson(response.getEntity(), com.matera.crudmicroservices.core.entities.Person.class);

        Assert.assertEquals(Long.valueOf(3), person.getId());
        Assert.assertEquals("Willie Barrett", person.getName());
        Assert.assertEquals("202-555-0155", person.getPhoneNumber());
    }

    @Test
    public void all() throws Exception {

        final String uri = URL + "/person/all";
        HttpResponse response = doGET(uri);

        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatusLine().getStatusCode());

        List<com.matera.crudmicroservices.core.entities.Person> persons = Utils.fromJson(response.getEntity(),
            new TypeReference<List<com.matera.crudmicroservices.core.entities.Person>>() {
            });
        Assert.assertEquals(5, persons.size());
    }

    @Test
    public void filterByName() throws Exception {

        final String uri = URL + "/person/all?name=Andre%20Grant";
        HttpResponse response = doGET(uri);

        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatusLine().getStatusCode());

        List<com.matera.crudmicroservices.core.entities.Person> persons = Utils.fromJson(response.getEntity(),
            new TypeReference<List<com.matera.crudmicroservices.core.entities.Person>>() {
            });
        Assert.assertEquals(2, persons.size());
    }

    @Test
    public void filterByPhoneNumber() throws Exception {

        final String uri = URL + "/person/all?phoneNumber=202-555-0155";
        HttpResponse response = doGET(uri);

        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatusLine().getStatusCode());

        List<com.matera.crudmicroservices.core.entities.Person> persons = Utils.fromJson(response.getEntity(),
            new TypeReference<List<com.matera.crudmicroservices.core.entities.Person>>() {
            });
        Assert.assertEquals(2, persons.size());
    }

    @Test
    public void filterByNameAndPhoneNumber() throws Exception {

        final String uri = URL + "/person/all?name=Andre%20Grant&phoneNumber=202-555-0155";
        HttpResponse response = doGET(uri);

        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatusLine().getStatusCode());

        List<com.matera.crudmicroservices.core.entities.Person> persons = Utils.fromJson(response.getEntity(),
            new TypeReference<List<com.matera.crudmicroservices.core.entities.Person>>() {
            });
        Assert.assertEquals(1, persons.size());

        Assert.assertEquals(Long.valueOf(5), persons.get(0).getId());
        Assert.assertEquals("Andre Grant", persons.get(0).getName());
        Assert.assertEquals("202-555-0155", persons.get(0).getPhoneNumber());
    }

    @Test
    public void createOk() throws Exception {

        com.matera.crudmicroservices.core.entities.Person expected = PersonConverter
            .toEntity(Person.builder().withId(9999L).withName("Joe Doe").withPhoneNumber("000-000-0000").build());

        HttpResponse response = doPOST(URL + "/person", expected);
        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatusLine().getStatusCode());

        com.matera.crudmicroservices.core.entities.Person actual =
            Utils.fromJson(response.getEntity(), com.matera.crudmicroservices.core.entities.Person.class);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createWithNotUniqueId() throws Exception {

        com.matera.crudmicroservices.core.entities.Person expected = PersonConverter
            .toEntity(Person.builder().withId(1L).withName("Joe Doe").withPhoneNumber("000-000-0000").build());

        HttpResponse response = doPOST(URL + "/person", expected);

        Assert.assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatusLine().getStatusCode());
    }

    @Test
    public void updateOk() throws Exception {

        final long id = 1L;

        com.matera.crudmicroservices.core.entities.Person expected = PersonConverter
            .toEntity(Person.builder().withId(id).withName("Joe Doe").withPhoneNumber("000-000-0000").build());

        HttpResponse response = doPUT(URL + "/person/" + id, expected);
        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatusLine().getStatusCode());

        com.matera.crudmicroservices.core.entities.Person actual =
            Utils.fromJson(response.getEntity(), com.matera.crudmicroservices.core.entities.Person.class);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateNonExistingId() throws Exception {

        final long id = 9998L;

        com.matera.crudmicroservices.core.entities.Person expected = PersonConverter
            .toEntity(Person.builder().withId(id).withName("Joe Doe").withPhoneNumber("000-000-0000").build());

        HttpResponse response = doPUT(URL + "/person/" + id, expected);
        Assert.assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatusLine().getStatusCode());
    }

    @Test
    public void delete() throws Exception {

        final long id = 1L;

        com.matera.crudmicroservices.core.entities.Person expected = PersonConverter
            .toEntity(Person.builder().withId(id).withName("Joe Doe").withPhoneNumber("000-000-0000").build());

        HttpResponse response = doDELETE(URL + "/person/" + id);
        Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatusLine().getStatusCode());

        final String uri = URL + "/person/all";
        response = doGET(uri);

        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatusLine().getStatusCode());

        List<com.matera.crudmicroservices.core.entities.Person> persons = Utils.fromJson(response.getEntity(),
            new TypeReference<List<com.matera.crudmicroservices.core.entities.Person>>() {
            });
        Assert.assertEquals(4, persons.size());
    }

    private HttpResponse doGET(String uri) throws Exception {

        HttpGet request = new HttpGet(uri);
        request.addHeader("Content-Type", "application/json");
        return new DefaultHttpClient().execute(request);
    }

    private HttpResponse doPOST(String uri, Object entity) throws Exception {

        HttpPost request = new HttpPost(uri);
        request.addHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity(Utils.toJson(entity)));
        return new DefaultHttpClient().execute(request);
    }

    private HttpResponse doPUT(String uri, Object entity) throws Exception {

        HttpPut request = new HttpPut(uri);
        request.addHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity(Utils.toJson(entity)));
        return new DefaultHttpClient().execute(request);
    }

    private HttpResponse doDELETE(String uri) throws Exception {

        HttpDelete request = new HttpDelete(uri);
        return new DefaultHttpClient().execute(request);
    }

}
