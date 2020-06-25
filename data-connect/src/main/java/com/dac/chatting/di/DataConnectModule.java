package com.dac.chatting.di;

import com.dac.chatting.repository.AccountsRepository;
import com.dac.chatting.repository.pg.AccountsPgRepository;
import com.github.pgasync.ConnectionPoolBuilder;
import com.github.pgasync.Db;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.typesafe.config.Config;

public class DataConnectModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountsRepository.class).to(AccountsPgRepository.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    public Db database(Config config) {
        final Config pgConfig = config.getConfig("postgres-pool-config");
        return new ConnectionPoolBuilder()
            .hostname(pgConfig.getString("host"))
            .port(pgConfig.getInt("port"))
            .database(pgConfig.getString("database"))
            .username(pgConfig.getString("user"))
            .password(pgConfig.getString("password"))
            .poolSize(pgConfig.getInt("pool-size"))
            .build();
    }
}
