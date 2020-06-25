package com.dac.chatting.entities;

import com.dac.chatting.exceptions.NullArgumentException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class SqlStatement {

    public static final String QUERY_ALL = "SELECT * FROM accounts";

    @Contract(pure = true)
    public static @NotNull(exception = NullArgumentException.class) String WHERE(final String statement) {
        return QUERY_ALL + " WHERE " + statement;
    }
}
