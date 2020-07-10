package com.dac.chatting.di;

import com.dac.chatting.api.AccountApi;
import com.dac.chatting.api.AuthenticationApi;
import com.dac.chatting.api.MainApi;
import com.dac.chatting.api.rest.ApiRest;
import com.dac.chatting.api.rest.routes.AccountRoute;
import com.dac.chatting.api.rest.routes.AuthenticationRoute;
import com.google.inject.AbstractModule;

public class EndpointModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MainApi.class).to(ApiRest.class).asEagerSingleton();
        bind(AccountApi.class).to(AccountRoute.class).asEagerSingleton();
        bind(AuthenticationApi.class).to(AuthenticationRoute.class).asEagerSingleton();
    }
}
