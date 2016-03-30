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
 * Hystrix command to create a Person
 *
 * @author egzefer
 */
public class PersonCreateCommand extends HystrixCommand<Person> {

    private static final HystrixCommand.Setter SETTER = Setter.withGroupKey(CrudmicroservicesGroupKeys.MIDDLE)
        .andCommandKey(HystrixCommandKey.Factory.asKey(PersonCreateCommand.class.getName()));

    public static final String DEFAULT_URL = "/crudmicroservicesmiddle/person";

    public static final String URL = "crudmicroservices.person.create.url";

    private ObjectMapper mapper;

    private final RestClient restClient;

    private Person person;

    public PersonCreateCommand(ObjectMapper mapper, final RestClient restClient, Person person) {

        super(SETTER);
        this.mapper = mapper;

        this.restClient = restClient;
        this.person = person;
    }

    @Override
    protected Person run() throws Exception {

        String personCreateURL = DynamicPropertyFactory.getInstance().getStringProperty(URL, DEFAULT_URL).get();

        URI URI = UriBuilder.fromPath(personCreateURL).build();

        HttpRequest request = HttpRequest.newBuilder().verb(Verb.POST).uri(URI).entity(person).build();

        try (HttpResponse response = restClient.executeWithLoadBalancer(request)) {
            return mapper.readValue(response.getInputStream(), Person.class);
        }
    }

}
