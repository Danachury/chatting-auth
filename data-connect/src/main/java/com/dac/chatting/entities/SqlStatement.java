package com.dac.chatting.entities;

import com.dac.chatting.exceptions.NullArgumentException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class SqlStatement {

    public static final String QUERY_ALL = "SELECT * FROM accounts";

    @Contract(pure = true)
    public static @NotNull(exception = NullArgumentException.class)
    String WHERE(String... params) {
        return QUERY_ALL + " WHERE " + buildWhere(params);
    }

    @Contract(pure = true)
    public static @NotNull(exception = NullArgumentException.class)
    String buildWhere(String... statement) {
        // ToDo: Replace this builder by other implementation
        return String.join(" = ?", statement);
    }
}
