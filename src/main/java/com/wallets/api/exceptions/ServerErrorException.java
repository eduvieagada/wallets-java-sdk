package com.wallets.api.exceptions;

public class ServerErrorException extends Exception {
    public ServerErrorException(String message) {
        super(message);
    }
}
