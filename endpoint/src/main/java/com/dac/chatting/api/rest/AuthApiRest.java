package com.dac.chatting.api.rest;

import akka.http.javadsl.server.Route;
import com.dac.chatting.api.Api;
import com.dac.chatting.api.handlers.CustomExceptionHandler;
import com.dac.chatting.api.handlers.CustomRejectionHandler;
import com.dac.chatting.api.rest.routes.AuthRoute;
import com.dac.chatting.api.rest.routes.BaseRoute;
import com.google.inject.Inject;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import java.util.function.Supplier;

import static akka.http.javadsl.model.StatusCodes.OK;
import static akka.http.javadsl.server.PathMatchers.segment;

@OpenAPIDefinition
@Path("/")
public class AuthApiRest extends BaseRoute implements Api, CustomExceptionHandler, CustomRejectionHandler {

    private final AuthRoute authRoute;

    @Inject
    public AuthApiRest(AuthRoute authRoute) {
        this.authRoute = authRoute;
    }

    @Override
    public Route mainRoute() {
        return concat(
            api_10(() -> concat(this.authRoute.authenticate(), root())),
            mapping(),
            docs(),
            root()
        );
    }

    /**
     * Swagger Json mappings
     */
    private Route mapping() {
        return pathPrefix("mapping",
            () -> getFromResourceDirectory("mapping")
        );
    }

    /**
     * Swagger ui bundle (html, css...)
     */
    private Route docs() {
        return pathPrefix("docs",
            () -> getFromResourceDirectory("swagger-ui")
        );
    }

    /**
     * Useful for connectivity test purposes.
     * Empty relative path or slash, at the end
     */
    @Operation(
        method = "GET",
        summary = "Returns OK if service is available")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Success OK"))
    @Path("/")
    @Consumes("application/json")
    public Route root() {
        return pathEndOrSingleSlash(() ->
            get(() -> complete(OK))
        );
    }

    private Route api_10(final Supplier<Route> innerRoute) {
        return pathPrefix(segment("api").slash("v1"), innerRoute);
    }

}
