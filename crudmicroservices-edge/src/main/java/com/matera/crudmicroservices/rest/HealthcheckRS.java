package com.matera.crudmicroservices.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @author wbatista
 */
@Path("services/v1/healthcheck")
public class HealthcheckRS {
    
    private final Logger LOG = LoggerFactory.getLogger(HealthcheckRS.class);

    @GET
    public Response getHealthcheck() {

        LOG.info("Getting the healthcheck status ...");
        return Response.ok("HEALTHY").build();
    }
}
