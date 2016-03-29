package com.matera.crudmicroservices.api.rest;

import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.api.command.FindAllPersonsCommand;
import com.matera.crudmicroservices.api.command.FindPersonByIdCommand;
import com.matera.crudmicroservices.api.command.PersonCreateCommand;
import com.matera.crudmicroservices.api.command.PersonUpdateCommand;
import com.matera.crudmicroservices.config.CrudmicroservicesGroupKeys;
import com.matera.crudmicroservices.core.entities.Person;
import com.netflix.client.ClientException;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpRequest.Verb;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.niws.client.http.RestClient;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;

/**
 * Client Rest implementation to handle middle responses
 *
 * @author egzefer
 * @author geiser
 */
public class RestPersonClient implements PersonClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestPersonClient.class);

    private static final String DEFAULT_DELETE_PERSON_URL = "crudmicroservicesmiddle/person/{id}";
    private static final String DELETE_PERSON_URL = "crudmicroservices.person.delete.url";

    private RestClient restClient;
    private ObjectMapper mapper;

    @Inject
    public RestPersonClient(RestClient restClient, com.fasterxml.jackson.databind.ObjectMapper mapper) {

        this.restClient = restClient;
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    public Observable<List<Person>> all(String name, String phoneNumber) {

        return new FindAllPersonsCommand(mapper, restClient, name, phoneNumber).observe();
    }

    /**
     * {@inheritDoc}
     */
    public Observable<Person> byId(Long id) {

        checkNotNull(id, "The given id musn't be null");
        return new FindPersonByIdCommand(mapper, restClient, id).observe();
    }

    /**
     * {@inheritDoc}
     */
    public Observable<Person> createPerson(Person person) {

        checkNotNull(person, "Person musn't be null");
        return new PersonCreateCommand(mapper, restClient, person).observe();
    }

    /**
     * {@inheritDoc}
     */
    public Observable<Person> updatePerson(Long id, Person person) {

        return new PersonUpdateCommand(mapper, restClient, id, person).observe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<Void> removePerson(Long id) {

        checkNotNull(id, "Person id must not be null");
        return new HystrixCommand<Void>(Setter.withGroupKey(CrudmicroservicesGroupKeys.MIDDLE)
            .andCommandKey(HystrixCommandKey.Factory.asKey("DeletePerson"))) {

            @Override
            protected Void run() throws Exception {

                final String url = DynamicPropertyFactory.getInstance()
                    .getStringProperty(DELETE_PERSON_URL, DEFAULT_DELETE_PERSON_URL).get();

                HttpRequest request = HttpRequest.newBuilder().verb(Verb.DELETE).uri(UriBuilder.fromPath(url).build(id))
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).build();

                try (HttpResponse response = restClient.execute(request)) {
                    Preconditions.checkArgument(response.isSuccess(), "Delete response was not successful");
                } catch (ClientException e) {
                    LOGGER.error("Error deleting person in middle", e);
                    throw e;
                }

                return null;
            }
        }.observe();
    }

}
