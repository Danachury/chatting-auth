package com.chatting;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import com.chatting.di.CommonModule;
import com.google.inject.Injector;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.Stage.PRODUCTION;

public class App {

    public static void main(String[] args) {
        final Injector injector = createInjector(PRODUCTION, new CommonModule("cht-auth-system"));
        final ActorSystem actorSystem = injector.getInstance(ActorSystem.class);
        final Materializer materializer = injector.getInstance(Materializer.class);
        new Server(10101, actorSystem, materializer, null)
            .run();
    }
}
