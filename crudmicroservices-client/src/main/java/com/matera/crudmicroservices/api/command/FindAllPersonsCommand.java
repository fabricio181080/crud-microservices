package com.matera.crudmicroservices.api.command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
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
import java.util.List;

import javax.ws.rs.core.UriBuilder;

/**
 * Hystrix command to find all persons
 * 
 * @author egzefer
 *
 */
public class FindAllPersonsCommand extends HystrixCommand<List<Person>> {

    private static final HystrixCommand.Setter SETTER = Setter.withGroupKey(CrudmicroservicesGroupKeys.MIDDLE)
        .andCommandKey(HystrixCommandKey.Factory.asKey(FindAllPersonsCommand.class.getName()));

    public static final String DEFAULT_URL = "/crudmicroservicesmiddle/person/all";

    public static final String URL = "crudmicroservices.person.findall.url";

    private ObjectMapper mapper;

    private final RestClient restClient;

    private String name;

    private String phoneNumber;

    public FindAllPersonsCommand(ObjectMapper mapper, final RestClient restClient, String name, String phoneNumber) {

        super(SETTER);
        this.mapper = mapper;
        this.restClient = restClient;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected List<Person> run() throws Exception {

        String findAllPersonsURL = DynamicPropertyFactory.getInstance().getStringProperty(URL, DEFAULT_URL).get();
        UriBuilder builder = UriBuilder.fromPath(findAllPersonsURL);
        
        if (!Strings.isNullOrEmpty(name)) {
            builder.queryParam("name", name);
        }
        if (!Strings.isNullOrEmpty(phoneNumber)) {
            builder.queryParam("phoneNumber", phoneNumber);
        }

        HttpRequest request = HttpRequest.newBuilder().verb(Verb.GET).uri(builder.build()).build();

        try (HttpResponse response = restClient.executeWithLoadBalancer(request)) {
            return mapper.readValue(response.getInputStream(), new TypeReference<List<Person>>() {
            });
        }
    }

}
