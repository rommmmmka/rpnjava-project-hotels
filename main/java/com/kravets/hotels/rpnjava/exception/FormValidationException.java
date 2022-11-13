package com.kravets.hotels.rpnjava.exception;

public class FormValidationException extends Exception {

    public FormValidationException() {
        super("Памылка валідацыі формы, паспрабуйце яшчэ раз");
    }
}
