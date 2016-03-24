package com.matera.crudmicroservices.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 *
 * @author wbatista
 */
@Path("services/v1/healthcheck")
public class HealthcheckRS {

    @GET
    public Response getHealthcheck() {

        return Response.ok("HEALTHY").build();
    }
}
