package com.kravets.hotels.rpnjava.exception;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {
        super("Карыстальнік з такім логінам ужо існуе");
    }
}
