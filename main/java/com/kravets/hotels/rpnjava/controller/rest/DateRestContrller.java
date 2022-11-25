package com.kravets.hotels.rpnjava.controller.rest;

import com.kravets.hotels.rpnjava.misc.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DateRestContrller {

    @GetMapping(value = "/rest/server_date")
    public ResponseEntity<Object> getServerDate() {
        Map<String, Object> answer = new HashMap<>();
        answer.put("today", DateUtils.convertDateToString(DateUtils.getCurrentDate()));
        answer.put("tomorrow", DateUtils.convertDateToString(DateUtils.getCurrentDate().plusDays(1)));
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }
}
