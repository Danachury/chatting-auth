package com.dac.chatting;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import com.dac.chatting.api.Api;
import com.dac.chatting.di.CommonModule;
import com.dac.chatting.di.DataConnectModule;
import com.dac.chatting.di.DomainModule;
import com.dac.chatting.di.EndpointModule;
import com.google.inject.Injector;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.Stage.PRODUCTION;

public class App {

    public static void main(String[] args) {
        final Injector injector = buildInjector();
        new Server(
            10101,
            injector.getInstance(ActorSystem.class),
            injector.getInstance(Materializer.class),
            injector.getInstance(Api.class)
        ).run();
    }

    private static Injector buildInjector() {
        return createInjector(
            PRODUCTION,
            new CommonModule("cht-auth-system"),
            new DataConnectModule(),
            new DomainModule(),
            new EndpointModule()
        );
    }
}
