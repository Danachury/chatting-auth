package com.dac.chatting.services;

import java.util.concurrent.CompletionStage;

public interface AuthenticationService {

    /**
     * Build a JWT token according {@param phone}
     *
     * @param phone id to build token
     * @return {@link CompletionStage} of {@link String} instance.
     * new token for {@param phone}
     */
    CompletionStage<String> generateToken(String phone);

    /**
     * Refresh {@param token}.
     *
     * @param token JWT token
     * @return {@link CompletionStage} of {@link String} instance.
     * <b>Refreshed {@param token}</b>
     */
    CompletionStage<String> refreshToken(String token);

    /**
     * Delete {@param token}
     *
     * @param token JWT token
     * @return {@link CompletionStage} of {@link Boolean} instance.
     * <b>TRUE</b> if {@param token} is deleted Successfully
     */
    CompletionStage<Boolean> deleteToken(String token);

}
