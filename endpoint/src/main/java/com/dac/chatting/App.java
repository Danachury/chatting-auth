package com.dac.chatting;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import com.dac.chatting.api.AuthApi;
import com.dac.chatting.di.CommonModule;
import com.google.inject.Injector;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.Stage.PRODUCTION;

public class App {

    public static void main(String[] args) {
        final Injector injector = createInjector(PRODUCTION, new CommonModule("cht-auth-system"));
        new Server(
            10101,
            injector.getInstance(ActorSystem.class),
            injector.getInstance(Materializer.class),
            injector.getInstance(AuthApi.class)
        ).run();
    }
}
