package com.kravets.hotels.rpnjava.controller.web;

import com.kravets.hotels.rpnjava.data.form.SearchForm;
import com.kravets.hotels.rpnjava.data.other.RoomWithFreeRoomsLeft;
import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.misc.DateUtils;
import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import com.kravets.hotels.rpnjava.validator.SearchValidatior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexPageController {
    private final Services services;
    private final SessionCheck sessionCheck;
    private final SearchValidatior searchValidatior;

    @Autowired
    public IndexPageController(
            Services services,
            SessionCheck sessionCheck,
            SearchValidatior searchValidatior
    ) {
        this.services = services;
        this.sessionCheck = sessionCheck;
        this.searchValidatior = searchValidatior;
    }

    @GetMapping("/")
    public String indexPage(Model model, HttpServletRequest request) {
        sessionCheck.noRestrictionAccess(model, request);

        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("citiesList", services.cities.getAllCities());
        model.addAttribute("currentDate", DateUtils.convertDateToString(DateUtils.getCurrentDate()));
        model.addAttribute("currentDatePlusDay", DateUtils.convertDateToString(DateUtils.getCurrentDate().plusDays(1)));
        model.addAttribute("checkInDate", DateUtils.convertDateToString(DateUtils.getCurrentDate()));
        model.addAttribute("checkOutDate", DateUtils.convertDateToString(DateUtils.getCurrentDate().plusDays(1)));

        model.addAttribute("templateName", "index");
        model.addAttribute("templateType", "index");
        return "base";
    }

    @GetMapping(value = "/", params = {"city", "adultsCount", "childrenCount", "checkInDate", "checkOutDate"})
    public String searchResultsPage(
            Model model,
            @ModelAttribute SearchForm searchForm,
            BindingResult result,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            searchValidatior.validate(searchForm, result);
            if (result.hasErrors()) {
                throw new FormValidationException();
            }
            sessionCheck.noRestrictionAccess(model, request);

            model.addAttribute("searchForm", searchForm);
            model.addAttribute("citiesList", services.cities.getAllCities());
            model.addAttribute("currentDate", DateUtils.convertDateToString(DateUtils.getCurrentDate()));
            model.addAttribute("currentDatePlusDay", DateUtils.convertDateToString(DateUtils.getCurrentDate().plusDays(1)));
            model.addAttribute("checkInDate", DateUtils.convertDateToString(searchForm.getCheckInDate()));
            model.addAttribute("checkOutDate", DateUtils.convertDateToString(searchForm.getCheckOutDate()));

            List<RoomWithFreeRoomsLeft> roomsList = services.db.getEmptyRoomsWithFreeRoomsField(searchForm);
            model.addAttribute("roomsList", roomsList);

            model.addAttribute("templateName", "index");
            model.addAttribute("templateType", "results");
            return "base";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }
}
