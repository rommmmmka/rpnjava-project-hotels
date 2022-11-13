package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.form.SearchForm;
import com.kravets.hotels.rpnjava.misc.CurrentDate;
import com.kravets.hotels.rpnjava.misc.SessionChecker;
import com.kravets.hotels.rpnjava.service.CityService;
import com.kravets.hotels.rpnjava.service.SessionService;
import com.kravets.hotels.rpnjava.service.UserService;
import com.kravets.hotels.rpnjava.validator.SearchValidatior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

@Controller
public class IndexPageController {
    private final CityService cityService;
    private final SessionService sessionService;
    private final UserService userService;

    private final SearchValidatior searchValidatior;

    @Autowired
    public IndexPageController(CityService cityService,
                               SessionService sessionService,
                               UserService userService,
                               SearchValidatior searchValidatior) {
        this.cityService = cityService;
        this.sessionService = sessionService;
        this.userService = userService;

        this.searchValidatior = searchValidatior;
    }

    @GetMapping("/")
    public String indexPage(Model model, HttpServletRequest request) {
        SessionChecker.noRestrictionAccess(model, request, userService, sessionService);

        ZonedDateTime currentZonedDateTime = CurrentDate.getZonedDateTime();
        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("citiesList", cityService.getAllCities());
        model.addAttribute("currentDate", CurrentDate.convertToStringDate(currentZonedDateTime));
        model.addAttribute("currentDatePlusDay", CurrentDate.convertToStringDate(currentZonedDateTime.plusDays(1)));

        model.addAttribute("templateName", "index");
        return "base";
    }

    @GetMapping("/results")
    public String searchResultsPage(Model model,
                                    @ModelAttribute SearchForm searchForm,
                                    BindingResult result,
                                    HttpServletRequest request,
                                    RedirectAttributes redirectAttributes) {
        try {
            searchValidatior.validate(searchForm, result);
            if (result.hasErrors()) {
                System.out.println(result.getAllErrors().get(0).toString());
                throw new FormValidationException();
            }
            SessionChecker.noRestrictionAccess(model, request, userService, sessionService);

            model.addAttribute("templateName", "results");
            return "base";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }


    }
}
