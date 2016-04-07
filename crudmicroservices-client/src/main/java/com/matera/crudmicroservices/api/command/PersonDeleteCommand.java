package com.matera.crudmicroservices.api.command;

import static com.google.common.base.Preconditions.checkArgument;

import com.matera.crudmicroservices.config.CrudmicroservicesGroupKeys;
import com.netflix.client.ClientException;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpRequest.Verb;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.niws.client.http.RestClient;

import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Commands to call middle and delete a person.
 * 
 * @author geiser
 */
public class PersonDeleteCommand extends HystrixCommand<Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDeleteCommand.class);

    private static final HystrixCommand.Setter SETTER = Setter.withGroupKey(CrudmicroservicesGroupKeys.MIDDLE)
        .andCommandKey(HystrixCommandKey.Factory.asKey(PersonDeleteCommand.class.getSimpleName()));

    private static final String DEFAULT_DELETE_PERSON_URL = "/crudmicroservicesmiddle/person/{id}";
    private static final String DELETE_PERSON_URL = "crudmicroservices.person.delete.url";

    private final RestClient restClient;
    private final Long id;

    /**
     * Creates a new command from restclient.
     * 
     * @param restClient
     */
    public PersonDeleteCommand(final RestClient restClient, final Long id) {

        super(SETTER);
        this.restClient = restClient;
        this.id = id;
    }

    @Override
    protected Void run() throws Exception {

        final String url =
            DynamicPropertyFactory.getInstance().getStringProperty(DELETE_PERSON_URL, DEFAULT_DELETE_PERSON_URL).get();

        HttpRequest request =
            HttpRequest.newBuilder().verb(Verb.DELETE).uri(UriBuilder.fromPath(url).build(id)).build();

        try (HttpResponse response = restClient.executeWithLoadBalancer(request)) {
            checkArgument(response.isSuccess(), "Delete response was not successful");
        } catch (ClientException e) {
            LOGGER.error("Error deleting person in middle", e);
            throw e;
        }

        return null;
    }
}
