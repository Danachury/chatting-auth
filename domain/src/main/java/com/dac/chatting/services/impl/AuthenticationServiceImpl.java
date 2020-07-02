package com.dac.chatting.services.impl;

import com.dac.chatting.bussisness.bobj.Account;
import com.dac.chatting.services.AccountsService;
import com.dac.chatting.services.AuthenticationService;
import com.google.inject.Inject;
import io.reactivex.rxjava3.core.Observable;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountsService accountsService;

    @Inject
    public AuthenticationServiceImpl(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    /**
     * @see AuthenticationService#getToken(String)
     */
    @Override
    public Observable<String> getToken(String phone) {
        return this.accountsService
            .query(phone)
            .map(Account::phone);
    }

    /**
     * @see AuthenticationService#refreshToken(String)
     */
    @Override
    public Observable<String> refreshToken(String token) {
        return null;
    }

    /**
     * @see AuthenticationService#deleteToken(String)
     */
    @Override
    public Observable<Boolean> deleteToken(String token) {
        return null;
    }
}
