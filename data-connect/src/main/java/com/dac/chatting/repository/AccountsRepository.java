package com.dac.chatting.repository;

import com.dac.chatting.entities.Account;
import rx.Observable;

import java.util.Collection;

public interface AccountsRepository {

    /**
     * @return {@link Collection} of results
     */
    Observable<Collection<Account>> query();

    /**
     * @param phone criteria
     * @return {@link Account} That match with {@param phone}
     */
    Observable<Account> query(String phone);
}
