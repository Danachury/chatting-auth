package com.dac.chatting.bussisness.bobj;

import com.dac.chatting.entities.AccountEntity;
import com.dac.chatting.entities.ImmutableAccountEntity;
import com.dac.chatting.exceptions.NullArgumentException;
import org.immutables.value.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

@Value.Immutable
public interface Account {

    String phone();

    String password();


    @Contract(pure = true)
    @NotNull(exception = NullArgumentException.class)
    static @Unmodifiable Account fromEntity(@NotNull AccountEntity entity) {
        return ImmutableAccount
            .builder()
            .phone(entity.phone())
            .password(entity.password())
            .build();
    }

    @Contract(pure = true)
    @NotNull(exception = NullArgumentException.class)
    static @Unmodifiable AccountEntity toEntity(@NotNull ImmutableAccount account) {
        return ImmutableAccountEntity
            .builder()
            .phone(account.phone())
            .password(account.password())
            .build();
    }
}
