package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.misc.LoggedInChecker;
import com.kravets.hotels.rpnjava.service.SessionService;
import com.kravets.hotels.rpnjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexPageController {
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public IndexPageController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public String indexPage(Model model, HttpServletRequest request) {
        LoggedInChecker.noRestrictionAccess(model, request, userService, sessionService);

        return "index";
    }
}
