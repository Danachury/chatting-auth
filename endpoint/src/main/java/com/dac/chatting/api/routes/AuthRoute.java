package com.dac.chatting.api.routes;

import akka.http.javadsl.server.Route;
import com.dac.chatting.api.AuthRest;
import com.dac.chatting.api.handlers.CustomExceptionHandler;
import com.dac.chatting.api.handlers.CustomRejectionHandler;
import com.google.inject.Inject;

public class AuthRoute extends BaseRoute implements AuthRest, CustomExceptionHandler, CustomRejectionHandler {

    private final AuthRest authRest;

    @Inject
    public AuthRoute(AuthRest authRest) {
        this.authRest = authRest;
    }

    @Override
    public Route route() {
        return pathPrefix("authenticate", this.authRest::route);
    }
}
