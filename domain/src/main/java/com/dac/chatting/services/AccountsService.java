package com.dac.chatting.services;

import com.dac.chatting.bussisness.bobj.Account;
import io.reactivex.rxjava3.core.Observable;

import java.util.Collection;

public interface AccountsService {

    /**
     * @return {@link Collection} of {@link Account}
     */
    Observable<Collection<Account>> query();

    /**
     * @param phone criteria
     * @return The {@link Account} matching with {@param phone}
     */
    Observable<Account> authenticate(String phone);
}
