package com.dac.chatting.repository.pg;

import com.dac.chatting.entities.AccountEntity;
import com.dac.chatting.repository.AccountsRepository;
import com.github.jasync.sql.db.pool.ConnectionPool;
import com.github.jasync.sql.db.postgresql.PostgreSQLConnection;
import com.google.inject.Inject;
import io.reactivex.rxjava3.core.Observable;

import java.util.Collection;
import java.util.Collections;

import static com.dac.chatting.entities.SqlStatement.QUERY_ALL;
import static com.dac.chatting.entities.SqlStatement.WHERE;

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
            .fromFuture(this.pgPool.sendPreparedStatement(QUERY_ALL))
            .map(AccountEntity::fromQueryResult);
    }

    /**
     * @see AccountsRepository#query(String)
     */
    @Override
    public Observable<AccountEntity> query(String phone) {
        return Observable
            .fromFuture(this.pgPool.sendPreparedStatement(WHERE("phone"), Collections.singletonList(phone)))
            .map(queryResult -> queryResult.getRows().get(0))
            .map(AccountEntity::fromRow);
    }
}
