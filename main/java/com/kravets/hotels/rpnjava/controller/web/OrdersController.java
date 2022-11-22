package com.kravets.hotels.rpnjava.controller.web;

import com.kravets.hotels.rpnjava.data.entity.SessionEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import com.kravets.hotels.rpnjava.exception.NoFreeRoomsAvaliableException;
import com.kravets.hotels.rpnjava.exception.OrderDoesNotExistException;
import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value = "/orders")
    public String ordersPage(
            Model model,
            @RequestParam(required = false, defaultValue = "0") Long filterStatus,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            SessionEntity sessionEntity = sessionCheck.userAccess(model, request);
            UserEntity userEntity = sessionEntity.getUser();

            model.addAttribute("ordersList", services.db.getOrdersByUserAndStatusId(userEntity, filterStatus));
            model.addAttribute("statusesList", services.status.getAllStatuses());
            model.addAttribute("filterStatus", filterStatus);

            model.addAttribute("templateName", "orders");
            model.addAttribute("templateType", "user");
            return "base";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
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
            SessionEntity sessionEntity = sessionCheck.userAccess(model, request);
            if (!services.db.checkIfRoomIsEmpty(checkInDate, checkOutDate, roomId)) {
                throw new NoFreeRoomsAvaliableException();
            }

            services.db.createOrder(checkInDate, checkOutDate, sessionEntity.getUser(), roomId);

            redirectAttributes.addFlashAttribute("successMessage", "Заказ паспяхова створаны");
            return "redirect:/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping(value = "/orders/remove")
    public String removeOrderByUserAction(
            Model model,
            @RequestParam Long id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            SessionEntity sessionEntity = sessionCheck.userAccess(model, request);

            services.order.removeOrderByUser(id, sessionEntity.getUser());

            redirectAttributes.addFlashAttribute("successMessage", "Заказ паспяхова выдалены");
            return "redirect:/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }
}
