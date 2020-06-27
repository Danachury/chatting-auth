package com.dac.chatting.api.rest.routes;

import akka.http.javadsl.marshalling.Marshaller;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.server.Route;
import akka.stream.Materializer;
import akka.util.ByteString;
import com.dac.chatting.api.ApiAuth;
import com.dac.chatting.api.handlers.CustomExceptionHandler;
import com.dac.chatting.api.handlers.CustomRejectionHandler;
import com.dac.chatting.bussisness.bobj.Account;
import com.dac.chatting.services.AccountsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.http.javadsl.model.StatusCodes.INTERNAL_SERVER_ERROR;
import static akka.http.javadsl.model.StatusCodes.OK;
import static com.dac.chatting.adapters.AsyncAdapters.adaptObservable;
import static com.dac.chatting.json.JsonSupport.jsonMapper;

public class AuthRoute extends BaseRoute implements ApiAuth, CustomExceptionHandler, CustomRejectionHandler {

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
            extractStrictEntity(timeout, strict -> {
                final String phone = strict.getData().decodeString(ByteString.UTF_8());
                final CompletionStage<Account> stage = adaptObservable(this.accountsService.query(phone));
                return onComplete(() -> stage, accTry -> {
                    if (accTry.isSuccess()) {
                        try {
                            return complete(OK, jsonMapper().writeValueAsString(accTry.get()));
                        } catch (JsonProcessingException e) {
                            return complete(INTERNAL_SERVER_ERROR, "Failed mapping account.");
                        }
                    } else {
                        return complete(INTERNAL_SERVER_ERROR, "Something was wrong.");
                    }
                });
            })
        );
    }
}
