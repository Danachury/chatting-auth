package com.dac.chatting.api.routes;

import akka.event.LoggingAdapter;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.RejectionHandler;
import akka.http.javadsl.server.Route;
import com.dac.chatting.exceptions.ServiceExceptionHandler;
import com.dac.chatting.exceptions.ServiceRejectionHandler;

import java.util.function.Supplier;

public abstract class BaseRoute extends AllDirectives implements ServiceExceptionHandler, ServiceRejectionHandler {

    public Route wrapInner(final Supplier<Route> innerSupply, final LoggingAdapter log) {
        return handleExceptions(exceptionHandler(log, this), () ->
            handleRejections(
                rejectionHandler(log, this)
                    .withFallback(RejectionHandler.defaultHandler()),
                innerSupply
            )
        );
    }
}
