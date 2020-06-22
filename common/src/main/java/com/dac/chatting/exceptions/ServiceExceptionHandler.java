package com.dac.chatting.exceptions;

import akka.event.LoggingAdapter;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.ExceptionHandler;

public interface ServiceExceptionHandler {

    ExceptionHandler exceptionHandler(LoggingAdapter log, AllDirectives directives);
}
