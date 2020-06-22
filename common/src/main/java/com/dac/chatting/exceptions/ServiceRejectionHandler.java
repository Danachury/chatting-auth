package com.dac.chatting.exceptions;

import akka.event.LoggingAdapter;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.RejectionHandler;

public interface ServiceRejectionHandler {

    RejectionHandler rejectionHandler(LoggingAdapter log, AllDirectives directives);
}
