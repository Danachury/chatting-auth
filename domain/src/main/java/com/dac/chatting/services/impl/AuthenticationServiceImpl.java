package com.dac.chatting.services.impl;

import akka.japi.Pair;
import com.dac.chatting.jwt.JWTToken;
import com.dac.chatting.services.AccountsService;
import com.dac.chatting.services.AuthenticationService;
import com.google.inject.Inject;

import java.util.concurrent.CompletionStage;

import static com.dac.chatting.adapters.AsyncAdapters.adaptObservable;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountsService accountsService;

    @Inject
    public AuthenticationServiceImpl(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    /**
     * @see AuthenticationService#generateToken(String)
     */
    @Override
    public CompletionStage<String> generateToken(String phone) {
        return adaptObservable(this.accountsService
            .query(phone)
            .map(account -> JWTToken.create(Pair.apply("phone", account.phone())))
        );
    }

    /**
     * @see AuthenticationService#refreshToken(String)
     */
    @Override
    public CompletionStage<String> refreshToken(String token) {
        return null;
    }

    /**
     * @see AuthenticationService#deleteToken(String)
     */
    @Override
    public CompletionStage<Boolean> deleteToken(String token) {
        return null;
    }
}
