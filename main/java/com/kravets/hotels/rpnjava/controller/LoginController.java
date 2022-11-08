package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.misc.LoggedInChecker;
import com.kravets.hotels.rpnjava.service.SessionService;
import com.kravets.hotels.rpnjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public LoginController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            LoggedInChecker.loggedOutAccess(model, request, userService, sessionService);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }

        model.addAttribute("isAdmin", true);
        if (!model.containsAttribute("userEntity")) {
            model.addAttribute("userEntity", new UserEntity());
        }

        return "login";
    }

    @PostMapping("/login")
    public String loginAction(Model model,
                              @ModelAttribute UserEntity userEntity,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              RedirectAttributes redirectAttributes) {
        try {
            LoggedInChecker.loggedOutAccess(model, request, userService, sessionService);
            userEntity = userService.loginUser(userEntity);
            SessionEntity sessionEntity = sessionService.createSession(userEntity);
            response.addCookie(new Cookie("session_key", sessionEntity.getSessionKey()));
            response.addCookie(new Cookie("user_id", userEntity.getId().toString()));
            return "redirect:/";
        } catch (Exception e) {
            userEntity.setPasswordHash("");
            redirectAttributes.addFlashAttribute("userEntity", userEntity);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/login";
        }
    }
}
