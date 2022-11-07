package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.misc.LoggedInChecker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexPageController {
    private final LoggedInChecker loggedInChecker;

    public IndexPageController(LoggedInChecker loggedInChecker) {
        this.loggedInChecker = loggedInChecker;
    }

    @GetMapping("/")
    public String indexPage(Model model, HttpServletRequest request) {
        model = loggedInChecker.noRestrictionAccess(model, request);

        return "index";
    }
}
