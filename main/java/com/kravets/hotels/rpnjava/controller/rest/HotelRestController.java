package com.kravets.hotels.rpnjava.controller.rest;

import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    @GetMapping(value = "/rest/hotel/get")
    public ResponseEntity<Object> getHotelsList(
            @RequestParam(required = false) String sessionKey,
            @RequestParam(required = false, defaultValue = "0") Long filterCity,
            @RequestParam(required = false, defaultValue = "creationDate") String sortingProperty,
            @RequestParam(required = false, defaultValue = "descending") String sortingDirection
    ) {
        Map<String, Object> answer = new HashMap<>();
        try {
            sessionCheck.noRestrictionAccessRest(sessionKey);

            answer.put("citiesList", services.cities.getAllCities());
            answer.put("hotelsList", services.db.getHotelsWithRoomsCountByParameters(filterCity, sortingProperty, sortingDirection));
            answer.put("filterCity", filterCity);
            answer.put("sortingProperty", sortingProperty);
            answer.put("sortingDirection", sortingDirection);

            return new ResponseEntity<>(answer, HttpStatus.OK);
        } catch (Exception e) {
            answer.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
