package com.dac.chatting.api;

import akka.http.javadsl.server.Route;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@OpenAPIDefinition
public interface AuthRest {

    @Operation(
        method = "POST",
        responses = {},
        summary = "Return user authentication value"
    )
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Success OK"))
    Route authenticate();
}
