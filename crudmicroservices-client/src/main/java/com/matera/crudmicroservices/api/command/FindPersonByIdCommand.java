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
 * Hystrix command to find a person entity by its id
 * 
 * @author egzefer
 *
 */
public class FindPersonByIdCommand extends HystrixCommand<Person> {

    private static final HystrixCommand.Setter SETTER = Setter.withGroupKey(CrudmicroservicesGroupKeys.MIDDLE)
        .andCommandKey(HystrixCommandKey.Factory.asKey(FindPersonByIdCommand.class.getName()));

    public static final String DEFAULT_URL = "crudmicroservicesmiddle/person";

    public static final String URL = "crudmicroservices.person.findbyid.url";

    private ObjectMapper mapper;

    private final RestClient restClient;

    private Long id;

    public FindPersonByIdCommand(ObjectMapper mapper, final RestClient restClient, Long id) {

        super(SETTER);
        this.mapper = mapper;

        this.restClient = restClient;
        this.id = id;
    }

    @Override
    protected Person run() throws Exception {

        String findPersonByIdURL = DynamicPropertyFactory.getInstance().getStringProperty(URL, DEFAULT_URL).get();

        URI URI = UriBuilder.fromPath(findPersonByIdURL).build(id);

        HttpRequest request = HttpRequest.newBuilder().verb(Verb.GET).uri(URI).build();

        try (HttpResponse response = restClient.execute(request)) {
            return mapper.readValue(response.getInputStream(), Person.class);
        }
    }

}
