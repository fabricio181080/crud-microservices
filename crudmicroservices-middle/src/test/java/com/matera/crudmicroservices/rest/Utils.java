package com.matera.crudmicroservices.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

@SuppressWarnings("unchecked")
public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    private static ObjectMapper parser = new ObjectMapper();

    static {
        parser.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
        parser.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
    }

    public static <T> T fromJson(final HttpEntity entity, final Class<T> klazz) throws IOException {

        String json = EntityUtils.toString(entity);
        LOGGER.info("Json to decode:" + json);
        ByteArrayInputStream is = new ByteArrayInputStream(json.getBytes());
        return (T) parser.readValue(is, klazz);
    }

    public static <T> T fromJson(HttpEntity entity, TypeReference<T> type) throws IOException {

        String json = EntityUtils.toString(entity);
        LOGGER.info("Json to decode:" + json);
        ByteArrayInputStream is = new ByteArrayInputStream(json.getBytes());
        return (T) parser.readValue(is, type);
    }

}
