package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.misc.LoggedInChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller
public class EasterEggsController {

    private final LoggedInChecker loggedInChecker;

    @Autowired
    public EasterEggsController(LoggedInChecker loggedInChecker) {
        this.loggedInChecker = loggedInChecker;
    }

    @GetMapping("/privacypolicy")
    public String memesPage(Model model, HttpServletRequest request) {
        model = loggedInChecker.noRestrictionAccess(model, request);

        Random rand = new Random();
        model.addAttribute("memeNumber", rand.nextInt(7) + 1);
        return "privacypolicy";
    }

    @GetMapping("/termsofuse")
    public String anecdotesPage() {
        return "";
    }
}
