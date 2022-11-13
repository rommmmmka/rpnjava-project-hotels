package com.kravets.hotels.rpnjava.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("Карыстальнік з такім логінам не знойдзены");
    }
}
