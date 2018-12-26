package com.senchenko.aliens.exception;

public class AliensException extends Exception{
    public AliensException() {
    }

    public AliensException(String message) {
        super(message);
    }

    public AliensException(String message, Throwable cause) {
        super(message, cause);
    }

    public AliensException(Throwable cause) {
        super(cause);
    }
}
