package com.dac.chatting.repository.pg.statements;

import lombok.Builder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@Builder
public final class SqlStatement {

    public static final String SELECT = "SELECT * FROM";

    @Contract(pure = true)
    @NotNull
    public static String SELECT_FROM(String tableName) {
        return SELECT + " " + tableName;
    }

    @Contract(pure = true)
    @NotNull
    public static String SELECT_WHERE(String tableName, String... params) {
        return SELECT_FROM(tableName) + buildWhere(params);
    }

    @Contract(pure = true)
    @NotNull
    public static String INSERT(String tableName, String... columns) {
        return "INSERT INTO " + tableName + " (" + String.join(", ", columns) + ")";
    }

    @Contract(pure = true)
    @NotNull
    public static String buildWhere(String... statements) {
        return " WHERE" + Stream.of(statements).map(st -> " " + st + " = ? ").collect(joining("AND"));
    }
}
