package com.matera.crudmicroservices.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
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

    @Provides
    @Singleton
    @Named("ValidationObjectMapper")
    public ObjectMapper getObjectMapper() {

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JaxbAnnotationModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

}
