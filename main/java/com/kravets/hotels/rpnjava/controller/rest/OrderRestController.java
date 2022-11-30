package com.kravets.hotels.rpnjava.controller.rest;

import com.kravets.hotels.rpnjava.data.entity.SessionEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import com.kravets.hotels.rpnjava.data.form.AddOrderForm;
import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.exception.NoAccessException;
import com.kravets.hotels.rpnjava.exception.NoFreeRoomsAvaliableException;
import com.kravets.hotels.rpnjava.exception.OrderDoesNotExistException;
import com.kravets.hotels.rpnjava.misc.ResponseStatus;
import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import com.kravets.hotels.rpnjava.validator.AddOrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderRestController {
    private final Services services;
    private final SessionCheck sessionCheck;
    private final AddOrderValidator addOrderValidator;

    @Autowired
    public OrderRestController(Services services, SessionCheck sessionCheck, AddOrderValidator addOrderValidator) {
        this.services = services;
        this.sessionCheck = sessionCheck;
        this.addOrderValidator = addOrderValidator;
    }

    @GetMapping(value = "/api/order/get_list")
    public ResponseEntity<Object> getOrdersList(
            @RequestParam String sessionKey,
            @RequestParam(required = false, defaultValue = "0") Long filterStatus
    ) {
        try {
            SessionEntity sessionEntity = sessionCheck.userAccessRest(sessionKey);
            UserEntity userEntity = sessionEntity.getUser();

            return ResponseStatus.OK.body(services.db.getOrdersByUserAndStatusId(userEntity, filterStatus));
        } catch (Exception e) {
            return ResponseStatus.UNKNOWN.body(null);
        }
    }

    @PostMapping(value = "/api/order/add")
    public ResponseEntity<Object> addOrderAction(
            @RequestParam(required = false) String sessionKey,
            @ModelAttribute AddOrderForm addOrderForm,
            BindingResult result
    ) {
        try {
            SessionEntity sessionEntity = sessionCheck.userAccessRest(sessionKey);
            addOrderValidator.validate(addOrderForm, result);
            if (result.hasErrors()) {
                throw new FormValidationException();
            }
            if (!services.db.checkIfRoomIsEmpty(addOrderForm)) {
                throw new NoFreeRoomsAvaliableException();
            }

            services.db.createOrder(addOrderForm, sessionEntity.getUser());

            return ResponseStatus.OK.body(null);
        } catch (FormValidationException e) {
            return ResponseStatus.FORM_NOT_VALID.body(null);
        } catch (NoFreeRoomsAvaliableException e) {
            return ResponseStatus.NO_FREE_ROOMS.body(null);
        } catch (Exception e) {
            return ResponseStatus.UNKNOWN.body(null);
        }
    }

    @PostMapping(value = "/api/order/remove")
    public ResponseEntity<Object> removeOrderAction(
            @RequestParam String sessionKey,
            @RequestParam Long id
    ) {
        try {
            SessionEntity sessionEntity = sessionCheck.userAccessRest(sessionKey);

            services.order.removeOrderByUser(id, sessionEntity.getUser());

            return ResponseStatus.OK.body(null);
        } catch (NoAccessException e) {
            return ResponseStatus.NO_ACCESS.body(null);
        } catch (OrderDoesNotExistException e) {
            return ResponseStatus.ORDER_DOES_NOT_EXIST.body(null);
        } catch (Exception e) {
            return ResponseStatus.UNKNOWN.body(null);
        }
    }
}
