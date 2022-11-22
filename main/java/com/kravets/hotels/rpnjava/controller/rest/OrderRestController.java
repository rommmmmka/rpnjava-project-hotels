package com.kravets.hotels.rpnjava.controller.rest;

import com.kravets.hotels.rpnjava.data.entity.SessionEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import com.kravets.hotels.rpnjava.exception.NoFreeRoomsAvaliableException;
import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderRestController {
    private final Services services;
    private final SessionCheck sessionCheck;

    @Autowired
    public OrderRestController(Services services, SessionCheck sessionCheck) {
        this.services = services;
        this.sessionCheck = sessionCheck;
    }

    @GetMapping(value = "/rest/order/get")
    public ResponseEntity<Object> getOrdersList(
            @RequestParam(required = false) String sessionKey,
            @RequestParam(required = false, defaultValue = "0") Long filterStatus
    ) {
        Map<String, Object> answer = new HashMap<>();
        try {
            SessionEntity sessionEntity = sessionCheck.userAccessRest(sessionKey);
            UserEntity userEntity = sessionEntity.getUser();

            answer.put("ordersList", services.db.getOrdersByUserAndStatusId(userEntity, filterStatus));
            answer.put("statusesList", services.status.getAllStatuses());
            answer.put("filterStatus", filterStatus);

            return new ResponseEntity<>(answer, HttpStatus.OK);
        } catch (Exception e) {
            answer.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/rest/order/add")
    public ResponseEntity<Object> addOrderAction(
            @RequestParam(required = false) String sessionKey,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam Long roomId
    ) {
        Map<String, Object> answer = new HashMap<>();
        try {
            SessionEntity sessionEntity = sessionCheck.userAccessRest(sessionKey);
            if (!services.db.checkIfRoomIsEmpty(checkInDate, checkOutDate, roomId)) {
                throw new NoFreeRoomsAvaliableException();
            }

            services.db.createOrder(checkInDate, checkOutDate, sessionEntity.getUser(), roomId);

            return new ResponseEntity<>(answer, HttpStatus.OK);
        } catch (Exception e) {
            answer.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/rest/order/remove")
    public ResponseEntity<Object> removeOrderAction(
            @RequestParam(required = false) String sessionKey,
            @RequestParam Long id
    ) {
        Map<String, Object> answer = new HashMap<>();
        try {
            SessionEntity sessionEntity = sessionCheck.userAccessRest(sessionKey);

            services.order.removeOrderByUser(id, sessionEntity.getUser());

            return new ResponseEntity<>(answer, HttpStatus.OK);
        } catch (Exception e) {
            answer.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
        }
    }


}
