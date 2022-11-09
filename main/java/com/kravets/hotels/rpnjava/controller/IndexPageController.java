package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.form.SearchForm;
import com.kravets.hotels.rpnjava.misc.CitiesList;
import com.kravets.hotels.rpnjava.misc.LoggedInChecker;
import com.kravets.hotels.rpnjava.service.SessionService;
import com.kravets.hotels.rpnjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

        ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of("Europe/Minsk"));
        model.addAttribute("searchForm", new SearchForm(currentDate));
        model.addAttribute("citiesList", CitiesList.getCitiesList());
        model.addAttribute("currentDate", currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        model.addAttribute("currentDatePlusDay", currentDate.plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE));

        model.addAttribute("templateName", "index");
        return "base";
    }
}
