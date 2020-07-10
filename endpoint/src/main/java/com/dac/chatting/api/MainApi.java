package com.dac.chatting.api;

import akka.http.javadsl.server.Route;
import com.dac.chatting.Server;

public interface MainApi {

    /**
     * Main Server Api.
     * <br>
     * All other apis should be inherent from this.
     *
     * @see Server
     */
    Route mainRoute();
}
