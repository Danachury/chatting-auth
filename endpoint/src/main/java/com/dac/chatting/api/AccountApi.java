package com.dac.chatting.api;

import akka.http.javadsl.server.Route;

public interface AccountApi {

    Route create();

    Route delete();

    Route update();
}
