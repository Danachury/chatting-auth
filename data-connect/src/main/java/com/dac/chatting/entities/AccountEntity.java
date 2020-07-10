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

import static com.dac.chatting.date.DateUtil.DATE_FORMAT;
import static java.util.Objects.requireNonNull;

@Value.Immutable
public interface AccountEntity {

    Integer id();

    String phone();

    String email();

    String dialCode();

    String isoCode();

    Date creationDate();


    @Contract(pure = true)
    @NotNull
    static Collection<AccountEntity> fromQueryResult(@NotNull QueryResult queryResult) {
        return fromResultSet(queryResult.getRows());
    }

    @Contract(pure = true)
    @NotNull
    static Collection<AccountEntity> fromResultSet(@NotNull ResultSet resultSet) {
        return fromIterator(resultSet.iterator());
    }

    @Contract(pure = true)
    @NotNull
    static Collection<AccountEntity> fromIterator(@NotNull Iterator<RowData> iterator) {
        return StreamSupport
            .stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.IMMUTABLE), false)
            .map(AccountEntity::fromRow)
            .collect(Collectors.toUnmodifiableList());
    }

    @Contract(pure = true)
    @NotNull
    static @Unmodifiable AccountEntity fromRow(@NotNull RowData row) {
        return ImmutableAccountEntity
            .builder()
            .id(requireNonNull(row.getInt("id")))
            .phone(requireNonNull(row.getString("phone")))
            .email(requireNonNull(row.getString("email")))
            .dialCode(requireNonNull(row.getString("dial_code")))
            .isoCode(requireNonNull(row.getString("iso_code")))
            .creationDate(requireNonNull(row.getDate("creation_date")).toDate())
            .build();
    }

    default List<String> values() {
        return Arrays.asList(
            this.phone(),
            this.email(),
            this.dialCode(),
            this.isoCode(),
            DATE_FORMAT.format(this.creationDate())
        );
    }
}
