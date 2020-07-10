package com.dac.chatting.repository.pg;

import com.dac.chatting.entities.AccountEntity;
import com.dac.chatting.exceptions.AccountNotFoundException;
import com.dac.chatting.exceptions.RepositoryException;
import com.dac.chatting.repository.AccountsRepository;
import com.github.jasync.sql.db.pool.ConnectionPool;
import com.github.jasync.sql.db.postgresql.PostgreSQLConnection;
import com.google.inject.Inject;
import io.reactivex.rxjava3.core.Observable;

import java.util.Collection;

import static com.dac.chatting.repository.pg.statements.SqlStatement.*;
import static java.util.Collections.singletonList;

public class AccountsPgRepository implements AccountsRepository {

    private static final String tableName = "accounts";
    private static final String[] columns = new String[]{"phone", "email", "dial_code", "iso_code", "creation_date"};

    private final ConnectionPool<PostgreSQLConnection> pgPool;

    @Inject
    public AccountsPgRepository(ConnectionPool<PostgreSQLConnection> pgPool) {
        this.pgPool = pgPool;
    }

    /**
     * @see AccountsRepository#create(AccountEntity)
     */
    @Override
    public Observable<Boolean> create(AccountEntity account) {
        return Observable
            .fromFuture(this.pgPool.sendPreparedStatement(INSERT(tableName, columns), account.values()))
            .map(t -> true)
            .onErrorResumeNext(throwable -> Observable.error(new RepositoryException(throwable)));
    }

    /**
     * @see AccountsRepository#query()
     */
    @Override
    public Observable<Collection<AccountEntity>> query() {
        return Observable
            .fromFuture(this.pgPool.sendPreparedStatement(SELECT_FROM(tableName)))
            .map(AccountEntity::fromQueryResult);
    }

    /**
     * @see AccountsRepository#query(String)
     */
    @Override
    public Observable<AccountEntity> query(String phone) {
        return Observable
            .fromFuture(this.pgPool.sendPreparedStatement(SELECT_WHERE(tableName, columns[0]), singletonList(phone)))
            .flatMap(queryResult -> {
                if (queryResult.getRows().isEmpty())
                    return Observable.error(new AccountNotFoundException("[" + phone + "] Not found."));
                return Observable.just(queryResult.getRows().get(0));
            })
            .map(AccountEntity::fromRow);
    }
}
