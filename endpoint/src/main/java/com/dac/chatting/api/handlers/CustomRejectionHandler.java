package com.dac.chatting.api.handlers;

import akka.event.LoggingAdapter;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.MissingQueryParamRejection;
import akka.http.javadsl.server.RejectionHandler;
import com.dac.chatting.exceptions.ServiceRejectionHandler;

public interface CustomRejectionHandler extends ServiceRejectionHandler {

    @Override
    default RejectionHandler rejectionHandler(LoggingAdapter log, AllDirectives directives) {
        return RejectionHandler
            .newBuilder()
            .handle(MissingQueryParamRejection.class, r -> directives.complete(StatusCodes.BAD_REQUEST))
            .build();
    }
}
