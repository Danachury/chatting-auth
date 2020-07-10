package com.dac.chatting.jwt;

import akka.japi.Pair;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public interface JWTToken {

    String ISSUER = "auth0";
    String SECRET = "auth0";

    @SafeVarargs
    static String create(@NotNull Pair<String, String>... claims) {
        return create(ISSUER, claims);
    }

    @SafeVarargs
    static String create(@NotNull String issuer, @NotNull Pair<String, String>... claims) {
        return create(issuer, SECRET, claims);
    }

    @SafeVarargs
    static String create(@NotNull String issuer, @NotNull String secret, @NotNull Pair<String, String>... claims) {
        final JWTCreator.Builder builder = JWT.create().withIssuer(issuer);
        Arrays.asList(claims).forEach(claim -> builder.withClaim(claim.first(), claim.second()));
        return builder.sign(Algorithm.HMAC256(secret));
    }
}
