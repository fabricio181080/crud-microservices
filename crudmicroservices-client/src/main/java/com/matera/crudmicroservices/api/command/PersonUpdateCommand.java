package com.matera.crudmicroservices.api.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.crudmicroservices.config.CrudmicroservicesGroupKeys;
import com.matera.crudmicroservices.core.entities.Person;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpRequest.Verb;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.niws.client.http.RestClient;

import java.net.URI;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * Hystrix command to update a Person
 * 
 * @author egzefer
 */
public class PersonUpdateCommand extends HystrixCommand<Person> {

    private static final HystrixCommand.Setter PERSON_UPDATE_SETTER = Setter.withGroupKey(
        CrudmicroservicesGroupKeys.MIDDLE).andCommandKey(
        HystrixCommandKey.Factory.asKey(PersonUpdateCommand.class.getSimpleName()));

    public static final String DEFAULT_PERSON_UPDATE_URL = "/crudmicroservicesmiddle/person/{id}";
    public static final String PERSON_UPDATE_URL = "crudmicroservices.person.update.url";

    private final RestClient restClient;
    private final ObjectMapper mapper;
    private final Long id;
    private final Person person;

    public PersonUpdateCommand(final RestClient restClient, final ObjectMapper mapper, Long id, Person person) {

        super(PERSON_UPDATE_SETTER);
        this.restClient = restClient;
        this.mapper = mapper;
        this.id = id;
        this.person = person;
    }

    @Override
    protected Person run() throws Exception {

        String personUpdateURL =
            DynamicPropertyFactory.getInstance().getStringProperty(PERSON_UPDATE_URL, DEFAULT_PERSON_UPDATE_URL).get();

        URI URI = UriBuilder.fromPath(personUpdateURL).build(id);

        HttpRequest request =
            HttpRequest.newBuilder().verb(Verb.PUT).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).uri(URI).entity(person).build();

        try (HttpResponse response = restClient.executeWithLoadBalancer(request)) {
            return mapper.readValue(response.getInputStream(), Person.class);
        }
    }
}
