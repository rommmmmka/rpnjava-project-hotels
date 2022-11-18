package com.kravets.hotels.rpnjava.exception;

public class NoFreeRoomsAvaliableException extends Exception{
    public NoFreeRoomsAvaliableException() {
        super("Вольных пакояў больш не засталося");
    }
}
