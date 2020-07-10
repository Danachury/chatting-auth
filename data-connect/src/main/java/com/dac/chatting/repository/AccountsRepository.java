package com.dac.chatting.repository;

import com.dac.chatting.entities.AccountEntity;
import io.reactivex.rxjava3.core.Observable;

import java.util.Collection;

public interface AccountsRepository {

    /**
     * Create given {@param account}
     *
     * @param account to create
     * @return TRUE if account is created successfully.
     */
    Observable<Boolean> create(AccountEntity account);

    /**
     * @return {@link Collection} of {@link AccountEntity}
     */
    Observable<Collection<AccountEntity>> query();

    /**
     * @param phone criteria
     * @return {@link AccountEntity} That match with {@param phone}
     */
    Observable<AccountEntity> query(String phone);
}
