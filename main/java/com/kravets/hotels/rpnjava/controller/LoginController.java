package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.form.LoginForm;
import com.kravets.hotels.rpnjava.misc.SessionChecker;
import com.kravets.hotels.rpnjava.service.LoginService;
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
    private final LoginService loginService;
    private final SessionChecker sessionChecker;


    @Autowired
    public LoginController(LoginService loginService, SessionChecker sessionChecker) {
        this.loginService = loginService;
        this.sessionChecker = sessionChecker;
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            sessionChecker.loggedOutAccess(model, request);
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
            sessionChecker.loggedOutAccess(model, request);

            UserEntity userEntity = loginService.loginUser(loginForm);
            SessionEntity sessionEntity = loginService.createSession(userEntity);

            response.addCookie(new Cookie("session_key", sessionEntity.getSessionKey()));
            response.addCookie(new Cookie("user_id", userEntity.getId().toString()));
            return "redirect:/";
        } catch (Exception e) {
            loginForm.setPassword("");
            redirectAttributes.addFlashAttribute("loginForm", loginForm);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/login";
        }
    }
}
