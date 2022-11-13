package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.misc.SessionChecker;
import com.kravets.hotels.rpnjava.service.SessionService;
import com.kravets.hotels.rpnjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller
public class EasterEggsController {
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public EasterEggsController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/privacypolicy")
    public String memesPage(Model model, HttpServletRequest request) {
        SessionChecker.noRestrictionAccess(model, request, userService, sessionService);

        Random rand = new Random();
        model.addAttribute("memeNumber", rand.nextInt(7) + 1);

        model.addAttribute("templateName", "privacypolicy");
        return "base";
    }

    @GetMapping("/termsofuse")
    public String anecdotesPage(Model model, HttpServletRequest request) {
        SessionChecker.noRestrictionAccess(model, request, userService, sessionService);
        return "";
    }
}
