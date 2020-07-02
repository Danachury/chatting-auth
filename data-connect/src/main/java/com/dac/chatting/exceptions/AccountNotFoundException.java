package com.dac.chatting.exceptions;

public class AccountNotFoundException extends RepositoryException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
