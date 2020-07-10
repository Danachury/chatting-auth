package com.dac.chatting.di;

import com.dac.chatting.services.AccountsService;
import com.dac.chatting.services.AuthenticationService;
import com.dac.chatting.services.impl.AccountsServiceImpl;
import com.dac.chatting.services.impl.AuthenticationServiceImpl;
import com.google.inject.AbstractModule;

public class DomainModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountsService.class).to(AccountsServiceImpl.class).asEagerSingleton();
        bind(AuthenticationService.class).to(AuthenticationServiceImpl.class).asEagerSingleton();
    }
}
