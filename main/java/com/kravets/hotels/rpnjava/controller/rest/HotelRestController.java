package com.kravets.hotels.rpnjava.controller.rest;

import com.kravets.hotels.rpnjava.data.other.HotelWithRoomsCount;
import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HotelRestController {
    private final Services services;
    private final SessionCheck sessionCheck;

    @Autowired
    public HotelRestController(Services services, SessionCheck sessionCheck) {
        this.services = services;
        this.sessionCheck = sessionCheck;
    }

    @GetMapping(value = "/api/hotel/get_list")
    public ResponseEntity<Object> getHotelsList(
            @RequestParam(required = false) String sessionKey,
            @RequestParam(required = false, defaultValue = "0") Long filterCity,
            @RequestParam(required = false, defaultValue = "creationDate") String sortingProperty,
            @RequestParam(required = false, defaultValue = "descending") String sortingDirection
    ) {
        try {
            sessionCheck.noRestrictionAccessRest(sessionKey);
            return new ResponseEntity<>(services.db.getHotelsWithRoomsCountByParameters(filterCity, sortingProperty, sortingDirection), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
