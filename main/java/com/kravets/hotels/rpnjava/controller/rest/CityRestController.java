package com.kravets.hotels.rpnjava.controller.rest;

import com.kravets.hotels.rpnjava.misc.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CityRestController {
    private final Services services;

    @Autowired
    public CityRestController(Services services) {
        this.services = services;
    }

    @GetMapping(value = "/api/city/get_list")
    public ResponseEntity<Object> getCitiesList() {
        Map<String, Object> answer = new HashMap<>();
        answer.put("citiesList", services.cities.getAllCities());
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }
}
