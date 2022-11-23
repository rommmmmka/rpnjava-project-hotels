package com.kravets.hotels.rpnjava.exception;

public class OrderAlreadyPayedException extends Exception {
    public OrderAlreadyPayedException() {
        super("Заказ ужо аплачаны");
    }
}
