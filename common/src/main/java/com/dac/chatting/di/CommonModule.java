package com.dac.chatting.di;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import com.dac.chatting.config.ConfigProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommonModule extends AbstractModule {

    private final String systemName;

    @Override
    protected void configure() {
        bind(Config.class).toInstance(ConfigProvider.config);
    }

    @Provides
    @Singleton
    ActorSystem actorSystem(final Config config) {
        return ActorSystem.create(systemName, config);
    }

    @Provides
    @Singleton
    Materializer materializer(final ActorSystem actorSystem) {
        return Materializer.apply(actorSystem);
    }
}
