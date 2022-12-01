package com.kravets.hotels.rpnjava.misc;

import org.springframework.http.ResponseEntity;

public class ResponseStatus {
    public static final ResponseEntity.BodyBuilder OK = ResponseEntity.status(200);

    public static final ResponseEntity.BodyBuilder NO_ACCESS = ResponseEntity.status(403);
    public static final ResponseEntity.BodyBuilder INVALID_FILTERS = ResponseEntity.status(460);
    public static final ResponseEntity.BodyBuilder FORM_NOT_VALID = ResponseEntity.status(461);
    public static final ResponseEntity.BodyBuilder NO_FREE_ROOMS = ResponseEntity.status(462);
    public static final ResponseEntity.BodyBuilder ORDER_DOES_NOT_EXIST = ResponseEntity.status(463);
    public static final ResponseEntity.BodyBuilder USER_ALREADY_EXISTS = ResponseEntity.status(464);
    public static final ResponseEntity.BodyBuilder USER_NOT_FOUND = ResponseEntity.status(465);
    public static final ResponseEntity.BodyBuilder INVALID_PASSWORD = ResponseEntity.status(466);

    public static final ResponseEntity.BodyBuilder UNKNOWN = ResponseEntity.status(560);
}
