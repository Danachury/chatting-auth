package com.dac.chatting.repository.pg;

import com.dac.chatting.entities.AccountEntity;
import com.dac.chatting.exceptions.AccountNotFoundException;
import com.dac.chatting.repository.AccountsRepository;
import com.github.jasync.sql.db.pool.ConnectionPool;
import com.github.jasync.sql.db.postgresql.PostgreSQLConnection;
import com.google.inject.Inject;
import io.reactivex.rxjava3.core.Observable;

import java.util.Collection;
import java.util.Collections;

import static com.dac.chatting.repository.pg.statements.SqlStatement.SELECT_FROM;
import static com.dac.chatting.repository.pg.statements.SqlStatement.SELECT_WHERE;

public class AccountsPgRepository implements AccountsRepository {

    private final ConnectionPool<PostgreSQLConnection> pgPool;

    @Inject
    public AccountsPgRepository(ConnectionPool<PostgreSQLConnection> pgPool) {
        this.pgPool = pgPool;
    }

    /**
     * @see AccountsRepository#query()
     */
    @Override
    public Observable<Collection<AccountEntity>> query() {
        return Observable
            .fromFuture(this.pgPool.sendPreparedStatement(SELECT_FROM("accounts")))
            .map(AccountEntity::fromQueryResult);
    }

    /**
     * @see AccountsRepository#query(String)
     */
    @Override
    public Observable<AccountEntity> query(String phone) {
        return Observable
            .fromFuture(this.pgPool.sendPreparedStatement(SELECT_WHERE("accounts", "phone"), Collections.singletonList(phone)))
            .flatMap(queryResult -> {
                if (queryResult.getRows().isEmpty())
                    return Observable.error(new AccountNotFoundException("[" + phone + "] Not found."));
                return Observable.just(queryResult.getRows().get(0));
            })
            .map(AccountEntity::fromRow);
    }
}
