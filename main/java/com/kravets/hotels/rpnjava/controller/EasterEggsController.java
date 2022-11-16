package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.misc.SessionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller
public class EasterEggsController {
    private final SessionCheck sessionCheck;

    @Autowired
    public EasterEggsController(SessionCheck sessionCheck) {
        this.sessionCheck = sessionCheck;
    }

    @GetMapping("/privacypolicy")
    public String memesPage(Model model, HttpServletRequest request) {
        sessionCheck.noRestrictionAccess(model, request);

        Random rand = new Random();
        model.addAttribute("memeNumber", rand.nextInt(11) + 1);

        model.addAttribute("templateName", "privacypolicy");
        return "base";
    }

    @GetMapping("/termsofuse")
    public String anecdotesPage(Model model, HttpServletRequest request) {
        sessionCheck.noRestrictionAccess(model, request);
        return "";
    }
}
