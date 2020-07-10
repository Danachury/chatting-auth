package com.dac.chatting.api.rest.routes;

import akka.http.javadsl.marshalling.Marshaller;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.server.Route;
import akka.stream.Materializer;
import akka.util.ByteString;
import com.dac.chatting.api.AuthenticationApi;
import com.dac.chatting.api.handlers.CustomExceptionHandler;
import com.dac.chatting.api.handlers.CustomRejectionHandler;
import com.dac.chatting.exceptions.AccountNotFoundException;
import com.dac.chatting.services.AccountsService;
import com.dac.chatting.services.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.http.javadsl.model.StatusCodes.*;
import static com.dac.chatting.json.JsonSupport.jsonMapper;

@Slf4j
public class AuthenticationRoute extends BaseRoute implements AuthenticationApi, CustomExceptionHandler, CustomRejectionHandler {

    private final Marshaller<ByteString, ByteString> jwtRender = Marshaller.withFixedContentType(ContentTypes.APPLICATION_JSON, t -> t);
    private static final FiniteDuration timeout = FiniteDuration.create(3, TimeUnit.SECONDS);

    private final AccountsService accountsService;
    private final AuthenticationService authenticationService;
    private final Materializer materializer;

    @Inject
    public AuthenticationRoute(AccountsService accountsService, Materializer materializer, AuthenticationService authenticationService) {
        this.accountsService = accountsService;
        this.authenticationService = authenticationService;
        this.materializer = materializer;
    }

    @Override
    public Route authenticate() {
        return pathPrefix("authenticate", () ->
            extractStrictEntity(timeout, strict -> {

                final String phone = strict.getData().decodeString(ByteString.UTF_8());
                final CompletionStage<String> stage = this.authenticationService.generateToken(phone);
                log.info("Querying user " + phone);

                return onComplete(stage, accTry -> {
                    if (accTry.isSuccess()) {
                        try {
                            return complete(OK, jsonMapper().writeValueAsString(accTry.get()));
                        } catch (JsonProcessingException e) {
                            return complete(INTERNAL_SERVER_ERROR, "Failed mapping account.");
                        }
                    } else {
                        final Exception exception = accTry.failed().getOrElse(() -> new Exception("Something was wrong."));
                        if (exception instanceof AccountNotFoundException) {
                            return complete(NO_CONTENT);
                        }
                        return complete(INTERNAL_SERVER_ERROR, exception.getMessage());
                    }
                });
            })
        );
    }

    @Override
    public Route[] v1Routes() {
        return new Route[]{
            this.authenticate()
        };
    }
}
