package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.form.LoginForm;
import com.kravets.hotels.rpnjava.form.RegisterForm;
import com.kravets.hotels.rpnjava.misc.SessionChecker;
import com.kravets.hotels.rpnjava.service.SessionService;
import com.kravets.hotels.rpnjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegisterController {
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public RegisterController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/register")
    public String registerPage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            SessionChecker.loggedOutAccess(model, request, userService, sessionService);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }

        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm());
        }

        model.addAttribute("templateName", "register");
        return "base";
    }

    @PostMapping("/register")
    public String registerAction(Model model,
                                 @Valid @ModelAttribute RegisterForm registerForm,
                                 BindingResult result,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                throw new FormValidationException();
            }
            SessionChecker.loggedOutAccess(model, request, userService, sessionService);

            userService.registerUser(registerForm);
            LoginForm loginForm = new LoginForm(registerForm.getLogin());
            redirectAttributes.addFlashAttribute("loginForm", loginForm);
            redirectAttributes.addFlashAttribute("successMessage", "Акаўнт паспяхова створаны!");

            return "redirect:/login";
        } catch (Exception e) {
            registerForm.setPassword("");
            redirectAttributes.addFlashAttribute("registerForm", registerForm);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }
}
