package com.dac.chatting.di;

import com.dac.chatting.repository.AccountsRepository;
import com.dac.chatting.repository.pg.AccountsPgRepository;
import com.github.jasync.sql.db.ConnectionPoolConfiguration;
import com.github.jasync.sql.db.pool.ConnectionPool;
import com.github.jasync.sql.db.postgresql.PostgreSQLConnection;
import com.github.jasync.sql.db.postgresql.PostgreSQLConnectionBuilder;
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
    public ConnectionPool<PostgreSQLConnection> database(Config config) {
        final Config pgConfig = config.getConfig("postgres-pool-config");
        final ConnectionPoolConfiguration pgPool = new ConnectionPoolConfiguration(
            pgConfig.getString("host"),
            pgConfig.getInt("port"),
            pgConfig.getString("database"),
            pgConfig.getString("user"),
            pgConfig.getString("password"),
            pgConfig.getInt("pool-size")
        );
        return PostgreSQLConnectionBuilder.createConnectionPool(pgPool);
    }
}
