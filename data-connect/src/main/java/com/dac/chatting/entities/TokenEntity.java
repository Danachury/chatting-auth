package com.dac.chatting.entities;

import com.github.jasync.sql.db.QueryResult;
import com.github.jasync.sql.db.ResultSet;
import com.github.jasync.sql.db.RowData;
import org.immutables.value.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

@Value.Immutable
public interface TokenEntity {

    Integer id();

    Integer accountId();

    String token();

    Long expirationTime();

    Date creationDate();

    Date lastRefreshDate();


    @Contract(pure = true)
    static Collection<TokenEntity> fromQueryResult(@NotNull QueryResult queryResult) {
        return fromResultSet(queryResult.getRows());
    }

    @Contract(pure = true)
    static Collection<TokenEntity> fromResultSet(@NotNull ResultSet resultSet) {
        return fromIterator(resultSet.iterator());
    }

    @Contract(pure = true)
    static Collection<TokenEntity> fromIterator(@NotNull Iterator<RowData> iterator) {
        return StreamSupport
            .stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.IMMUTABLE), false)
            .map(TokenEntity::fromRow)
            .collect(Collectors.toUnmodifiableList());
    }

    @Contract(pure = true)
    static @Unmodifiable @NotNull TokenEntity fromRow(@NotNull RowData row) {
        return ImmutableTokenEntity
            .builder()
            .id(requireNonNull(row.getInt("id")))
            .accountId(requireNonNull(row.getInt("id_account")))
            .token(requireNonNull(row.getString("token")))
            .expirationTime(requireNonNull(row.getLong("expiration_time")))
            .creationDate(requireNonNull(row.getDate("creation_date")).toDate())
            .lastRefreshDate(requireNonNull(row.getDate("last_refresh_date")).toDate())
            .build();
    }
}
