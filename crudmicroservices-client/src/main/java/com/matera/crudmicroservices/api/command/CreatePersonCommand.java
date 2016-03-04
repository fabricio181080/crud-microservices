package com.matera.crudmicroservices.api.command;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class CreatePersonCommand extends HystrixCommand<Person> {

    private static final HystrixCommand.Setter CREATE_PERSON_SETTER =
        Setter.withGroupKey(CrudmicroservicesGroupKeys.MIDDLE)
            .andCommandKey(HystrixCommandKey.Factory.asKey(CreatePersonCommand.class.getName()));

    public static final String DEFAULT_CREATE_PERSON_URL = "crudmicroservicesmiddle/person";

    public static final String CREATE_PERSON_URL = "crudmicroservices.person.create.url";

    private static final Logger LOG = LoggerFactory.getLogger(CreatePersonCommand.class);

    private ObjectMapper mapper;

    private final RestClient restClient;

    private Person person;

    public CreatePersonCommand(ObjectMapper mapper, final RestClient restClient, Person person) {

        super(CREATE_PERSON_SETTER);
        this.mapper = mapper;

        this.restClient = restClient;
        this.person = person;
    }

    @Override
    protected Person run() throws Exception {

        String createPersonURL =
            DynamicPropertyFactory.getInstance().getStringProperty(CREATE_PERSON_URL, DEFAULT_CREATE_PERSON_URL).get();

        URI URI = UriBuilder.fromPath(createPersonURL).build();

        HttpRequest request = HttpRequest.newBuilder().verb(Verb.POST).uri(URI).entity(person).build();

        try (HttpResponse response = restClient.execute(request)) {
            return mapper.readValue(response.getInputStream(), Person.class);
        }
    }

    @Override
    protected Person getFallback() {

        Throwable exception = getFailedExecutionException();
        if (exception != null) {
            LOG.error("Error creating person: " + person, exception);
        }

        return null;
    }

}
