package com.matera.crudmicroservices.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.matera.crudmicroservices.core.config.CrudMicroservicesObjectMapperProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    private static ObjectMapper parser = new CrudMicroservicesObjectMapperProvider().get();

    static {
        parser.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
    }

    public static <T> T fromJson(final HttpEntity entity, final Class<T> klazz) throws IOException {

        String json = EntityUtils.toString(entity);
        LOGGER.info("Json to decode:" + json);
        ByteArrayInputStream is = new ByteArrayInputStream(json.getBytes());
        return parser.readValue(is, klazz);
    }

    public static <T> T fromJson(HttpEntity entity, TypeReference<T> type) throws IOException {

        String json = EntityUtils.toString(entity);
        LOGGER.info("Json to decode:" + json);
        ByteArrayInputStream is = new ByteArrayInputStream(json.getBytes());
        return (T) parser.readValue(is, type);
    }

    public static String toJson(Object obj) throws IOException {

        return parser.writeValueAsString(obj);
    }
}
