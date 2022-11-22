package com.kravets.hotels.rpnjava.controller.web;

import com.kravets.hotels.rpnjava.data.entity.SessionEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import com.kravets.hotels.rpnjava.data.form.AddOrderForm;
import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.exception.NoFreeRoomsAvaliableException;
import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import com.kravets.hotels.rpnjava.validator.AddOrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OrdersController {
    private final Services services;
    private final SessionCheck sessionCheck;
    private final AddOrderValidator addOrderValidator;

    @Autowired
    public OrdersController(Services services, SessionCheck sessionCheck, AddOrderValidator addOrderValidator) {
        this.services = services;
        this.sessionCheck = sessionCheck;
        this.addOrderValidator = addOrderValidator;
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
            System.out.println(services.db.getOrdersByUserAndStatusId(userEntity, filterStatus).get(0).getExpireDateTimeFormatted());
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
            @ModelAttribute AddOrderForm addOrderForm,
            BindingResult result,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        try {
            SessionEntity sessionEntity = sessionCheck.userAccess(model, request);
            addOrderValidator.validate(addOrderForm, result);
            if (result.hasErrors()) {
                throw new FormValidationException();
            }
            if (!services.db.checkIfRoomIsEmpty(addOrderForm)) {
                throw new NoFreeRoomsAvaliableException();
            }

            services.db.createOrder(addOrderForm, sessionEntity.getUser());

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
