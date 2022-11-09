package com.kravets.hotels.rpnjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logoutAction(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        Cookie cookie = new Cookie("session_key", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        cookie = new Cookie("user_id", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        redirectAttributes.addFlashAttribute("successMessage", "Вы выйшли з акаўнта");
        return "redirect:/";
    }
}
