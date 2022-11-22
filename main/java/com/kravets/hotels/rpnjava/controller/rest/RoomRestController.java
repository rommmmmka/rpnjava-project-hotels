package com.kravets.hotels.rpnjava.controller.rest;

import com.kravets.hotels.rpnjava.data.form.SearchForm;
import com.kravets.hotels.rpnjava.data.other.RoomWithFreeRoomsLeft;
import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.misc.DateUtils;
import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import com.kravets.hotels.rpnjava.validator.SearchValidatior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RoomRestController {
    private final Services services;
    private final SessionCheck sessionCheck;
    private final SearchValidatior searchValidatior;

    @Autowired
    public RoomRestController(Services services, SessionCheck sessionCheck, SearchValidatior searchValidatior) {
        this.services = services;
        this.sessionCheck = sessionCheck;
        this.searchValidatior = searchValidatior;
    }

    @GetMapping(value = "/rest/room/get")
    public ResponseEntity<Object> getRoomsList(
            @RequestParam(required = false) String sessionKey,
            @RequestParam(required = false, defaultValue = "0") Long filterHotel,
            @RequestParam(required = false, defaultValue = "0") Long filterCity,
            @RequestParam(required = false, defaultValue = "creationDate") String sortingProperty,
            @RequestParam(required = false, defaultValue = "descending") String sortingDirection
    ) {
        Map<String, Object> answer = new HashMap<>();
        try {
            sessionCheck.noRestrictionAccessRest(sessionKey);

            answer.put("hotelsList", services.hotel.getAllHotels());
            answer.put("roomsList", services.db.getRoomsByParameters(filterHotel, filterCity, sortingProperty, sortingDirection));
            answer.put("citiesList", services.cities.getAllCities());
            answer.put("filterHotel", filterHotel);
            answer.put("filterCity", filterCity);
            answer.put("sortingProperty", sortingProperty);
            answer.put("sortingDirection", sortingDirection);

            return new ResponseEntity<>(answer, HttpStatus.OK);
        } catch (Exception e) {
            answer.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/rest/room/search")
    public ResponseEntity<Object> searchRooms(
            @RequestParam(required = false) String sessionKey,
            @ModelAttribute SearchForm searchForm,
            BindingResult result
    ) {
        Map<String, Object> answer = new HashMap<>();
        try {
            searchValidatior.validate(searchForm, result);
            if (result.hasErrors()) {
                throw new FormValidationException();
            }
            sessionCheck.noRestrictionAccessRest(sessionKey);

            answer.put("searchForm", searchForm);
            answer.put("citiesList", services.cities.getAllCities());
            answer.put("currentDate", DateUtils.convertDateToString(DateUtils.getCurrentDate()));
            answer.put("currentDatePlusDay", DateUtils.convertDateToString(DateUtils.getCurrentDate().plusDays(1)));
            answer.put("checkInDate", DateUtils.convertDateToString(searchForm.getCheckInDate()));
            answer.put("checkOutDate", DateUtils.convertDateToString(searchForm.getCheckOutDate()));

            List<RoomWithFreeRoomsLeft> roomsList = services.db.getEmptyRoomsWithFreeRoomsField(searchForm);
            answer.put("roomsList", roomsList);

            return new ResponseEntity<>(answer, HttpStatus.OK);
        } catch (Exception e) {
            answer.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
        }
    }
}
