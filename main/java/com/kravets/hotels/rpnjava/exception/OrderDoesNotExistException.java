package com.kravets.hotels.rpnjava.exception;

public class OrderDoesNotExistException extends Exception {
    public OrderDoesNotExistException() {
        super("Заказ не існуе");
    }
}
