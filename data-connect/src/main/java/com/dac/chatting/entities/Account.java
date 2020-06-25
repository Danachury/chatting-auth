package com.dac.chatting.entities;

import com.github.pgasync.ResultSet;
import com.github.pgasync.Row;
import org.immutables.value.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Value.Immutable()
public interface Account {

    String phone();

    String password();

    static Collection<Account> fromResultSet(ResultSet resultSet) {
        return fromIterator(resultSet.iterator());
    }

    static Collection<Account> fromIterator(Iterator<Row> iterator) {
        return StreamSupport
            .stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.IMMUTABLE), false)
            .map(Account::fromRow)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    static Account fromRow(Row row) {
        return ImmutableAccount
            .builder()
            .phone(row.getString("phone"))
            .phone(row.getString("password"))
            .build();
    }
}
