package com.dac.chatting.jwt;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * Represent <b>header</b> of JWT.
 * <br>
 * The header typically consists of two parts: the type of the token, which is JWT,
 * <br>
 * and the signing algorithm being used, such as HMAC SHA256 or RSA.
 *
 * @see <a href="https://jwt.io/introduction/">JWT Header doc</a>
 */
@Value.Immutable
@JsonSerialize
public interface TokenHeader {

    /**
     * Algorithm type
     */
    String alg();

    /**
     * Token type
     */
    String typ();
}
