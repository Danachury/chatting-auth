package com.dac.chatting.services;

import io.reactivex.rxjava3.core.Observable;

public interface AuthenticationService {

    /**
     * Build a JWT token according {@param phone}
     *
     * @param phone id to build token
     * @return {@link Observable} of {@link String} instance.
     * new token for {@param phone}
     */
    Observable<String> getToken(String phone);

    /**
     * Refresh {@param token}.
     *
     * @param token JWT token
     * @return {@link Observable} of {@link String} instance.
     * <b>Refreshed {@param token}</b>
     */
    Observable<String> refreshToken(String token);

    /**
     * Delete {@param token}
     *
     * @param token JWT token
     * @return {@link Observable} of {@link Boolean} instance.
     * <b>TRUE</b> if {@param token} is deleted Successfully
     */
    Observable<Boolean> deleteToken(String token);

}
