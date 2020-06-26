package com.dac.chatting.api.routes;

import akka.http.javadsl.marshalling.Marshaller;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.server.Route;
import akka.stream.Materializer;
import akka.util.ByteString;
import com.dac.chatting.api.AuthRest;
import com.dac.chatting.api.handlers.CustomExceptionHandler;
import com.dac.chatting.api.handlers.CustomRejectionHandler;
import com.dac.chatting.services.AccountsService;
import com.google.inject.Inject;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

import static akka.http.javadsl.model.StatusCodes.OK;

public class AuthRoute extends BaseRoute implements AuthRest, CustomExceptionHandler, CustomRejectionHandler {

    private final Marshaller<ByteString, ByteString> jwtRender = Marshaller.withFixedContentType(ContentTypes.APPLICATION_JSON, t -> t);
    private static final FiniteDuration timeout = FiniteDuration.create(3, TimeUnit.SECONDS);

    private final AccountsService accountsService;
    private final Materializer materializer;

    @Inject
    public AuthRoute(AccountsService accountsService, Materializer materializer) {
        this.accountsService = accountsService;
        this.materializer = materializer;
    }

    @Override
    public Route authenticate() {
        return pathPrefix("authenticate", () ->
            extractStrictEntity(timeout, strict ->
                complete(OK, strict.getData().decodeString(ByteString.UTF_8()))
            )
        );
    }
}
