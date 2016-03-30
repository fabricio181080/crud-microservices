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

import javax.ws.rs.core.UriBuilder;

/**
 * Hystrix command to update a Person
 * 
 * @author egzefer
 */
public class PersonUpdateCommand extends HystrixCommand<Person> {

    private static final HystrixCommand.Setter PERSON_UPDATE_SETTER =
        Setter.withGroupKey(CrudmicroservicesGroupKeys.MIDDLE)
            .andCommandKey(HystrixCommandKey.Factory.asKey(PersonUpdateCommand.class.getName()));

    public static final String DEFAULT_PERSON_UPDATE_URL = "/crudmicroservicesmiddle/person/{id}";

    public static final String PERSON_UPDATE_URL = "crudmicroservices.person.update.url";

    private Long id;

    private ObjectMapper mapper;

    private final RestClient restClient;

    private Person person;

    public PersonUpdateCommand(ObjectMapper mapper, final RestClient restClient, Long id, Person person) {

        super(PERSON_UPDATE_SETTER);
        this.mapper = mapper;
        this.id = id;
        this.person = person;
        this.restClient = restClient;
    }

    @Override
    protected Person run() throws Exception {

        String personUpdateURL =
            DynamicPropertyFactory.getInstance().getStringProperty(PERSON_UPDATE_URL, DEFAULT_PERSON_UPDATE_URL).get();

        URI URI = UriBuilder.fromPath(personUpdateURL).build(id);

        HttpRequest request = HttpRequest.newBuilder().verb(Verb.PUT).uri(URI).entity(person).build();

        try (HttpResponse response = restClient.executeWithLoadBalancer(request)) {
            return mapper.readValue(response.getInputStream(), Person.class);
        }
    }

}
