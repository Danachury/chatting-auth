package com.dac.chatting.api.rest;

import akka.http.javadsl.server.Route;
import com.dac.chatting.api.MainApi;
import com.dac.chatting.api.handlers.CustomExceptionHandler;
import com.dac.chatting.api.handlers.CustomRejectionHandler;
import com.dac.chatting.api.rest.routes.AccountRoute;
import com.dac.chatting.api.rest.routes.AuthenticationRoute;
import com.dac.chatting.api.rest.routes.BaseRoute;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import java.util.Arrays;

import static akka.http.javadsl.model.StatusCodes.OK;
import static akka.http.javadsl.server.PathMatchers.segment;

@OpenAPIDefinition
@Slf4j
@Path("/")
public class ApiRest extends BaseRoute implements MainApi, CustomExceptionHandler, CustomRejectionHandler {

    private final AuthenticationRoute authenticationRoute;
    private final AccountRoute accountRoute;

    @Inject
    public ApiRest(AuthenticationRoute authenticationRoute, AccountRoute accountRoute) {
        this.authenticationRoute = authenticationRoute;
        this.accountRoute = accountRoute;
    }

    @Override
    public Route mainRoute() {
        return concat(
            apiV1(),
            mapping(),
            docs(),
            root()
        );
    }

    @Override
    protected Route[] v1Routes() {
        return ImmutableList
            .<Route[]>builder()
            .add(this.accountRoute.v1Routes())
            .add(this.authenticationRoute.v1Routes())
            .add(new Route[]{root()})
            .build()
            .stream()
            .flatMap(Arrays::stream)
            .toArray(Route[]::new);
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
        log.info("Health Check... Status code: 200");
        return pathEndOrSingleSlash(() -> complete(OK));
    }

    private Route apiV1() {
        return pathPrefix(
            segment("api"),
            () -> concat(
                get(this::root),
                path("v1", () -> concat(, this.v1Routes()))
            )
        );
    }

}
