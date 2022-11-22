package com.kravets.hotels.rpnjava.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String missingValue) {
        super("Карыстальнік з такім " + missingValue + " не знойдзены");
    }
}
