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
 * Hystrix command to find a person entity by its id
 * 
 * @author egzefer
 *
 */
public class FindPersonByIdCommand extends HystrixCommand<Person> {

    private static final HystrixCommand.Setter SETTER = Setter.withGroupKey(CrudmicroservicesGroupKeys.MIDDLE)
        .andCommandKey(HystrixCommandKey.Factory.asKey(FindPersonByIdCommand.class.getSimpleName()));

    public static final String DEFAULT_URL = "/crudmicroservicesmiddle/person/{id}";
    public static final String URL = "crudmicroservices.person.findbyid.url";

    private final RestClient restClient;
    private final ObjectMapper mapper;
    private final Long id;

    public FindPersonByIdCommand(final RestClient restClient, final ObjectMapper mapper, final Long id) {

        super(SETTER);
        this.restClient = restClient;
        this.mapper = mapper;
        this.id = id;
    }

    @Override
    protected Person run() throws Exception {

        String findPersonByIdURL = DynamicPropertyFactory.getInstance().getStringProperty(URL, DEFAULT_URL).get();

        URI URI = UriBuilder.fromPath(findPersonByIdURL).build(id);

        HttpRequest request =
            HttpRequest.newBuilder().verb(Verb.GET).header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).uri(URI)
                .build();

        try (HttpResponse response = restClient.executeWithLoadBalancer(request)) {
            return mapper.readValue(response.getInputStream(), Person.class);
        }
    }

}
