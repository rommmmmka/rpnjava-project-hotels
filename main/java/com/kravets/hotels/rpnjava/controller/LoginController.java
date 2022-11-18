package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.data.entity.SessionEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.data.form.LoginForm;
import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {
    private final Services services;
    private final SessionCheck sessionCheck;

    @Autowired
    public LoginController(Services services, SessionCheck sessionCheck) {
        this.services = services;
        this.sessionCheck = sessionCheck;
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            sessionCheck.loggedOutAccess(model, request);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }

        if (!model.containsAttribute("loginForm")) {
            model.addAttribute("loginForm", new LoginForm());
        }

        model.addAttribute("templateName", "login");
        return "base";
    }

    @PostMapping("/login")
    public String loginAction(
            Model model,
            @Valid @ModelAttribute LoginForm loginForm,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        try {
            if (result.hasErrors()) {
                throw new FormValidationException();
            }
            sessionCheck.loggedOutAccess(model, request);

            UserEntity userEntity = services.user.loginUser(loginForm);
            SessionEntity sessionEntity = services.session.createSession(userEntity, loginForm.isRememberMe());

            Cookie cookie = new Cookie("session_key", sessionEntity.getSessionKey());
            if (loginForm.isRememberMe()) {
                cookie.setMaxAge(7 * 24 * 60 * 60);
            }
            response.addCookie(cookie);
            return "redirect:/";
        } catch (Exception e) {
            loginForm.setPassword("");
            redirectAttributes.addFlashAttribute("loginForm", loginForm);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/login";
        }
    }
}
