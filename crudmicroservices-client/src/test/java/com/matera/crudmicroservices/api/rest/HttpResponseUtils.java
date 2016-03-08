package com.matera.crudmicroservices.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.netflix.client.ClientException;
import com.netflix.client.http.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.Map;

/**
 *
 *
 * @author wbatista, egzefer
 */
public class HttpResponseUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Create an HttpResponse using status and entity. Entity can be null.
     * 
     * @param status
     * @param entity
     * @return HttpResponse
     */
    public static HttpResponse createResponse(final int status, final Object entity) {

        return new HttpResponse() {

            @Override
            public boolean isSuccess() {

                return true;
            }

            @Override
            public boolean hasPayload() {

                return false;
            }

            @Override
            public URI getRequestedURI() {

                return null;
            }

            @Override
            public Object getPayload() throws ClientException {

                return null;
            }

            @Override
            public boolean hasEntity() {

                return entity != null;
            }

            @Override
            public InputStream getInputStream() throws ClientException {

                if (entity != null) {

                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                        mapper.writeValue(baos, entity);
                        return new ByteArrayInputStream(baos.toByteArray());

                    } catch (IOException e) {
                        throw new RuntimeException();
                    }

                }
                return null;
            }

            @Override
            public <T> T getEntity(final TypeToken<T> type) throws Exception {

                return null;
            }

            @Override
            public <T> T getEntity(final Class<T> type) throws Exception {

                return null;
            }

            @Override
            public int getStatus() {

                return status;
            }

            @Override
            public Map<String, Collection<String>> getHeaders() {

                return Maps.newHashMap();
            }

            @Override
            public void close() {

            }
        };
    }
}
