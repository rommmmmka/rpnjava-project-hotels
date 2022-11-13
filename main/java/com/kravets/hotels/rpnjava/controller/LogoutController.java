package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.misc.SessionChecker;
import com.kravets.hotels.rpnjava.service.SessionService;
import com.kravets.hotels.rpnjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LogoutController {
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public LogoutController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/logout")
    public String logoutAction(Model model,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               RedirectAttributes redirectAttributes) {
        try {
            SessionEntity sessionEntity = SessionChecker.userAccess(model, request, userService, sessionService);
            sessionService.deleteSession(sessionEntity);

            Cookie cookie = new Cookie("session_key", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            cookie = new Cookie("user_id", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            redirectAttributes.addFlashAttribute("successMessage", "Вы выйшли з акаўнта");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }
}
