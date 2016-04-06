package com.matera.crudmicroservices.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.matera.crudmicroservices.api.PersonClient;
import com.matera.crudmicroservices.api.rest.RestPersonClient;
import com.netflix.client.ClientFactory;
import com.netflix.niws.client.http.RestClient;

public class LocalRestCrudMicroservicesClientModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(PersonClient.class).to(RestPersonClient.class).in(Scopes.SINGLETON);
    }

    @Provides
    @Singleton
    @Named("ValidationRestClient")
    public RestClient getRestClient() {

        return (RestClient) ClientFactory.getNamedClient("crudmicroservicesmiddle");
    }
}
