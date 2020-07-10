package com.dac.chatting.services;

import com.dac.chatting.bussisness.bobj.Account;
import io.reactivex.rxjava3.core.Observable;

import java.util.Collection;

public interface AccountsService {

    /**
     * Create given {@param account}
     *
     * @param account to create
     * @return {@link Boolean#TRUE} if account is created successfully;
     * {@link Boolean#FALSE} if occur any {@link Exception}.
     */
    Observable<Boolean> create(Account account);

    /**
     * @return {@link Collection} of {@link Account}
     */
    Observable<Collection<Account>> query();

    /**
     * @param phone criteria
     * @return The {@link Account} matching with {@param phone}
     */
    Observable<Account> query(String phone);
}
