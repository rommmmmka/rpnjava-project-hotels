package com.kravets.hotels.rpnjava.controller.admin;

import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.form.EditUserForm;
import com.kravets.hotels.rpnjava.misc.SessionChecker;
import com.kravets.hotels.rpnjava.service.AdminService;
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
import javax.validation.Valid;

@Controller
public class AdminUsersController {
    private final AdminService adminService;
    private final SessionChecker sessionChecker;

    @Autowired
    public AdminUsersController(AdminService adminService, SessionChecker sessionChecker) {
        this.adminService = adminService;
        this.sessionChecker = sessionChecker;
    }

    @GetMapping("/admin/users")
    public String adminUsersPage(
            Model model,
            @RequestParam(required = false, defaultValue = "registrationDate") String sortingProperty,
            @RequestParam(required = false, defaultValue = "descending") String sortingDirection,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionChecker.adminAccess(model, request);

            model.addAttribute("sortingProperty", sortingProperty);
            model.addAttribute("sortingDirection", sortingDirection);
            model.addAttribute("usersList", adminService.getAllUsers(sortingProperty, sortingDirection));

            model.addAttribute("templateName", "users");
            return "base";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/admin/users/remove")
    public String removeUserAction(
            Model model,
            @RequestParam Long id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionChecker.adminAccess(model, request);

            adminService.removeUser(id);

            redirectAttributes.addFlashAttribute("successMessage", "Карыстальнік паспяхова выдалены");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/edit")
    public String editUserAction(
            Model model,
            @Valid @ModelAttribute EditUserForm editUserForm,
            BindingResult result,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            System.out.println(editUserForm);
            if (result.hasErrors()) {
                System.out.println(result.getAllErrors().toString());
                throw new FormValidationException();
            }
            sessionChecker.adminAccess(model, request);

            adminService.editUser(editUserForm);

            redirectAttributes.addFlashAttribute("successMessage", "Інфармацыя пра карыстальніка паспяхова зменена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/kill_sessions")
    public String killUserSessionsAction(
            Model model,
            @RequestParam Long id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionChecker.adminAccess(model, request);

            adminService.killUserSessions(id);

            redirectAttributes.addFlashAttribute("successMessage", "Сесіі карыстальніка паспяхова выдалены");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/users";
    }
}
