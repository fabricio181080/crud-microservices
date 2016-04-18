package com.matera.crudmicroservices.api.command;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * Hystrix command to find all persons
 * 
 * @author egzefer
 *
 */
public class FindAllPersonsCommand extends HystrixCommand<List<Person>> {

    private static final HystrixCommand.Setter SETTER = Setter.withGroupKey(CrudmicroservicesGroupKeys.MIDDLE)
        .andCommandKey(HystrixCommandKey.Factory.asKey(FindAllPersonsCommand.class.getSimpleName()));

    public static final String DEFAULT_URL = "/crudmicroservicesmiddle/person/all";
    public static final String URL = "crudmicroservices.person.findall.url";

    private final RestClient restClient;
    private final ObjectMapper mapper;
    private final String name;
    private final String phoneNumber;

    public FindAllPersonsCommand(final RestClient restClient, final ObjectMapper mapper, final String name,
        final String phoneNumber) {

        super(SETTER);
        this.restClient = restClient;
        this.mapper = mapper;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected List<Person> run() throws Exception {

        String findAllPersonsURL = DynamicPropertyFactory.getInstance().getStringProperty(URL, DEFAULT_URL).get();
        UriBuilder builder = UriBuilder.fromPath(findAllPersonsURL);

        if (isNotBlank(name)) {
            builder.queryParam("name", name);
        }

        if (isNotBlank(phoneNumber)) {
            builder.queryParam("phoneNumber", phoneNumber);
        }

        HttpRequest request =
            HttpRequest.newBuilder().verb(Verb.GET).header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .uri(builder.build()).build();

        try (HttpResponse response = restClient.executeWithLoadBalancer(request)) {
            return mapper.readValue(response.getInputStream(), new TypeReference<List<Person>>() {
            });
        }
    }

}
