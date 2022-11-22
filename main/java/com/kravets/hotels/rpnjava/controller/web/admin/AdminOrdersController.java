package com.kravets.hotels.rpnjava.controller.web.admin;

import com.kravets.hotels.rpnjava.data.entity.SessionEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminOrdersController {
    private final SessionCheck sessionCheck;
    private final Services services;

    @Autowired
    public AdminOrdersController(SessionCheck sessionCheck, Services services) {
        this.sessionCheck = sessionCheck;
        this.services = services;
    }

    @GetMapping(value = "/admin/orders")
    public String adminOrdersPage(
            Model model,
            @RequestParam(required = false, defaultValue = "0") Long filterStatus,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionCheck.adminAccess(model, request);

            model.addAttribute("ordersList", services.db.getOrdersByStatusId(filterStatus));
            model.addAttribute("statusesList", services.status.getAllStatuses());
            model.addAttribute("filterStatus", filterStatus);

            model.addAttribute("templateName", "orders");
            model.addAttribute("templateType", "admin");
            return "base";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping(value = "/admin/orders/edit")
    public String editOrderAction(
            Model model,
            @RequestParam Long id,
            @RequestParam Long status,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionCheck.adminAccess(model, request);

            services.db.editOrder(id, status);

            redirectAttributes.addFlashAttribute("successMessage", "Статус заказу паспяхова зменены");
            return "redirect:/admin/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping(value = "/admin/orders/remove")
    public String removeOrderByAdminAction(
            Model model,
            @RequestParam Long id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionCheck.adminAccess(model, request);

            services.order.removeOrder(id);

            redirectAttributes.addFlashAttribute("successMessage", "Заказ паспяхова выдалены");
            return "redirect:/admin/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }
}
