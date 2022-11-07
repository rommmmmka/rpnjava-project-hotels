package com.kravets.hotels.rpnjava.exception;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("Няправільны пароль");
    }
}
