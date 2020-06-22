package com.chatting.api;

import akka.http.javadsl.server.Route;

public interface RestApi {

    Route mainRoute();
}
