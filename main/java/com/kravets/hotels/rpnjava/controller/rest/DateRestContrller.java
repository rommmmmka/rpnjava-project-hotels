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

    @GetMapping(value = "/api/date/get_server_date")
    public ResponseEntity<Object> getServerDate() {
        Map<String, Object> answer = new HashMap<>();
        answer.put("currentDate", DateUtils.convertDateToString(DateUtils.getCurrentDate()));
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }
}
