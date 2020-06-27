package com.dac.chatting.di;

import com.dac.chatting.api.Api;
import com.dac.chatting.api.ApiAuth;
import com.dac.chatting.api.rest.AuthApiRest;
import com.dac.chatting.api.rest.routes.AuthRoute;
import com.google.inject.AbstractModule;

public class EndpointModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ApiAuth.class).to(AuthRoute.class).asEagerSingleton();
        bind(Api.class).to(AuthApiRest.class).asEagerSingleton();
    }
}
