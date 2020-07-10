package com.dac.chatting.api.rest.routes;

import akka.http.javadsl.server.Route;
import com.dac.chatting.api.AccountApi;
import com.dac.chatting.api.handlers.CustomExceptionHandler;
import com.dac.chatting.api.handlers.CustomRejectionHandler;

import static akka.http.javadsl.model.StatusCodes.OK;

public class AccountRoute extends BaseRoute implements AccountApi, CustomExceptionHandler, CustomRejectionHandler {

    @Override
    public Route create() {
        return complete(OK);
    }

    @Override
    public Route delete() {
        return complete(OK);
    }

    @Override
    public Route update() {
        return complete(OK);
    }

    @Override
    public Route[] v1Routes() {
        return new Route[]{
            this.create(),
            this.delete(),
            this.update()
        };
    }
}
