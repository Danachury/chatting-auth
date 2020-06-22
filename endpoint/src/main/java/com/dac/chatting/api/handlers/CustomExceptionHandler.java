package com.dac.chatting.api.handlers;

import akka.event.LoggingAdapter;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.ExceptionHandler;
import akka.http.scaladsl.server.CircuitBreakerOpenRejection;
import akka.http.scaladsl.server.MalformedQueryParamRejection;
import akka.japi.Option;
import akka.pattern.CircuitBreakerOpenException;
import com.dac.chatting.exceptions.ServiceExceptionHandler;

public interface CustomExceptionHandler extends ServiceExceptionHandler {

    @Override
    default ExceptionHandler exceptionHandler(final LoggingAdapter log, final AllDirectives directives) {
        return ExceptionHandler.newBuilder()
            .match(IllegalArgumentException.class, t ->
                directives.reject(MalformedQueryParamRejection.apply("", t.getMessage(), Option.<Throwable>option(t).asScala()))
            )
            .match(CircuitBreakerOpenException.class, t -> {
                log.error(t, t.getMessage());
                return directives.reject(CircuitBreakerOpenRejection.apply(t));
            })
            .match(Throwable.class, t -> {
                log.error(t, t.getMessage());
                return directives.complete(StatusCodes.INTERNAL_SERVER_ERROR);
            })
            .build();
    }
}
