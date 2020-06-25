package com.dac.chatting.exceptions;

public class NullArgumentException extends Exception {

    @SuppressWarnings("unused")
    public NullArgumentException(String message) {
        super("[null] argument not allowed.");
    }
}
