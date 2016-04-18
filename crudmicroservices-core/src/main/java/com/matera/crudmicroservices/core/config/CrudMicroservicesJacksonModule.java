package com.matera.crudmicroservices.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 *
 * @author wbatista
 */
public class CrudMicroservicesJacksonModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(ObjectMapper.class).annotatedWith(CrudMicroservices.class).toProvider(
            CrudMicroservicesObjectMapperProvider.class);
    }
    
    @Provides
    @Singleton
    public JacksonJsonProvider getJacksonJsonProvider(@CrudMicroservices final ObjectMapper mapper) {

        return new JacksonJsonProvider(mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
    }
}
