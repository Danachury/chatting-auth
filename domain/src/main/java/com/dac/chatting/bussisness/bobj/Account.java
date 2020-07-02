package com.dac.chatting.bussisness.bobj;

import com.dac.chatting.entities.AccountEntity;
import com.dac.chatting.entities.ImmutableAccountEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Date;

@Value.Immutable
@JsonSerialize
@JsonDeserialize
public interface Account {

    String phone();

    String email();

    String dialCode();

    String isoCode();

    Date creationDate();


    @Contract(pure = true)
    @NotNull
    static @Unmodifiable Account fromEntity(@NotNull AccountEntity entity) {
        return ImmutableAccount
            .builder()
            .phone(entity.phone())
            .email(entity.email())
            .dialCode(entity.dialCode())
            .isoCode(entity.isoCode())
            .creationDate(entity.creationDate())
            .build();
    }

    @Contract(pure = true)
    @NotNull
    static @Unmodifiable AccountEntity toEntity(@NotNull Account account) {
        return ImmutableAccountEntity
            .builder()
            .phone(account.phone())
            .email(account.email())
            .dialCode(account.dialCode())
            .isoCode(account.isoCode())
            .creationDate(account.creationDate())
            .build();
    }
}
