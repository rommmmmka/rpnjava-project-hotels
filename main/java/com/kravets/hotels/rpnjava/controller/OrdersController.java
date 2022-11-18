package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.exception.NoFreeRoomsAvaliableException;
import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class OrdersController {
    private final SessionCheck sessionCheck;
    private final Services services;

    @Autowired
    public OrdersController(SessionCheck sessionCheck, Services services) {
        this.sessionCheck = sessionCheck;
        this.services = services;
    }

    @PostMapping(value = "/orders/add")
    public String addOrderAction(
            Model model,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam Long roomId,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        try {
            sessionCheck.userAccess(model, request);
            if (!services.db.checkIfRoomIsEmpty(checkInDate, checkOutDate, roomId)) {
                throw new NoFreeRoomsAvaliableException();
            }

            services.order.createOrder(checkInDate, checkOutDate, roomId);

            return "redirect:/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }
}
