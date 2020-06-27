package com.dac.chatting.services.impl;

import com.dac.chatting.bussisness.bobj.Account;
import com.dac.chatting.repository.AccountsRepository;
import com.dac.chatting.services.AccountsService;
import com.google.inject.Inject;
import io.reactivex.rxjava3.core.Observable;

import java.util.Collection;
import java.util.stream.Collectors;

public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository;

    @Inject
    public AccountsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    /**
     * @see AccountsService#query()
     */
    @Override
    public Observable<Collection<Account>> query() {
        return this.accountsRepository
            .query()
            .map(accountEntities ->
                accountEntities
                    .stream()
                    .map(Account::fromEntity)
                    .collect(Collectors.toUnmodifiableList())
            );
    }

    /**
     * @see AccountsService#query(String)
     */
    @Override
    public Observable<Account> query(String phone) {
        return this.accountsRepository
            .query(phone)
            .map(Account::fromEntity);
    }
}
