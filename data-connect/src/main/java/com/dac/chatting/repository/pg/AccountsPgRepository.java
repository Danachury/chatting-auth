package com.dac.chatting.repository.pg;

import com.dac.chatting.entities.Account;
import com.dac.chatting.repository.AccountsRepository;
import com.github.pgasync.Db;
import com.google.inject.Inject;
import lombok.Builder;
import rx.Observable;

import java.util.Collection;

import static com.dac.chatting.entities.SqlStatement.QUERY_ALL;
import static com.dac.chatting.entities.SqlStatement.WHERE;

@Builder
public class AccountsPgRepository implements AccountsRepository {

    private final Db db;

    @Inject
    public AccountsPgRepository(Db db) {
        this.db = db;
    }

    /**
     * @see AccountsRepository#query()
     */
    @Override
    public Observable<Collection<Account>> query() {
        return this.db
            .querySet(QUERY_ALL)
            .map(Account::fromResultSet);
    }

    /**
     * @see AccountsRepository#query(String)
     */
    @Override
    public Observable<Account> query(String phone) {
        return this.db
            .querySet(WHERE("phone = ?"), phone)
            .map(resultSet -> resultSet.row(0))
            .map(Account::fromRow);
    }
}
