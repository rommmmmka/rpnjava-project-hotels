package com.kravets.hotels.rpnjava.controller.rest;

import com.kravets.hotels.rpnjava.data.entity.CityEntity;
import com.kravets.hotels.rpnjava.misc.ResponseStatus;
import com.kravets.hotels.rpnjava.misc.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityRestController {
    private final Services services;

    @Autowired
    public CityRestController(Services services) {
        this.services = services;
    }

    @GetMapping(value = "/api/city/get_list")
    public ResponseEntity<List<CityEntity>> getCitiesList() {
        return ResponseStatus.OK.body(services.cities.getAllCities());
    }
}
