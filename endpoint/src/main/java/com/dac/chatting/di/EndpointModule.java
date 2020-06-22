package com.dac.chatting.di;

import com.dac.chatting.RestApi;
import com.dac.chatting.api.AuthApi;
import com.dac.chatting.api.AuthRest;
import com.dac.chatting.api.routes.AuthRoute;
import com.google.inject.AbstractModule;

public class EndpointModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AuthRest.class).to(AuthRoute.class).asEagerSingleton();
        bind(RestApi.class).to(AuthApi.class).asEagerSingleton();
    }
}
