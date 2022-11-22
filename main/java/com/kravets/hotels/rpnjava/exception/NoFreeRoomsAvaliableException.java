package com.kravets.hotels.rpnjava.exception;

public class NoFreeRoomsAvaliableException extends Exception{
    public NoFreeRoomsAvaliableException() {
        super("Вольных нумароў больш не засталося");
    }
}
