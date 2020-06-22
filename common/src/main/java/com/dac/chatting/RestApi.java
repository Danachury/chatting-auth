package com.dac.chatting;

import akka.http.javadsl.server.Route;

public interface RestApi {

    Route mainRoute();
}
