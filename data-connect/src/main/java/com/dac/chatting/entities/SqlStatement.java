package com.dac.chatting.entities;

import com.dac.chatting.exceptions.NullArgumentException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public final class SqlStatement {

    public static final String QUERY_ALL = "SELECT * FROM accounts";

    @Contract(pure = true)
    public static @NotNull(exception = NullArgumentException.class)
    String WHERE(String... params) {
        return QUERY_ALL + buildWhere(params);
    }

    @Contract(pure = true)
    public static @NotNull(exception = NullArgumentException.class)
    String buildWhere(String... statements) {
        return " WHERE" + Stream.of(statements).map(st -> " " + st + " = ? ").collect(joining("AND"));
    }
}
