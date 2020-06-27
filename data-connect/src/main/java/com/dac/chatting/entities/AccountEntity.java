package com.dac.chatting.entities;

import com.dac.chatting.exceptions.NullArgumentException;
import com.github.jasync.sql.db.QueryResult;
import com.github.jasync.sql.db.ResultSet;
import com.github.jasync.sql.db.RowData;
import org.immutables.value.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

@Value.Immutable
public interface AccountEntity {

    String phone();

    String password();


    @Contract(pure = true)
    @NotNull(exception = NullArgumentException.class)
    static Collection<AccountEntity> fromQueryResult(@NotNull QueryResult queryResult) {
        return fromResultSet(queryResult.getRows());
    }

    @Contract(pure = true)
    @NotNull(exception = NullArgumentException.class)
    static Collection<AccountEntity> fromResultSet(@NotNull ResultSet resultSet) {
        return fromIterator(resultSet.iterator());
    }

    @Contract(pure = true)
    @NotNull(exception = NullArgumentException.class)
    static Collection<AccountEntity> fromIterator(@NotNull Iterator<RowData> iterator) {
        return StreamSupport
            .stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.IMMUTABLE), false)
            .map(AccountEntity::fromRow)
            .collect(Collectors.toUnmodifiableList());
    }

    @Contract(pure = true)
    @NotNull(exception = NullArgumentException.class)
    static @Unmodifiable AccountEntity fromRow(@NotNull RowData row) {
        return ImmutableAccountEntity
            .builder()
            .phone(requireNonNull(row.getString("phone")))
            .password(requireNonNull(row.getString("password")))
            .build();
    }
}
