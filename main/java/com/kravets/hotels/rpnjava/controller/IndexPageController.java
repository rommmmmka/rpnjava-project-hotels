package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.entity.RoomEntity;
import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.form.SearchForm;
import com.kravets.hotels.rpnjava.misc.CurrentDate;
import com.kravets.hotels.rpnjava.misc.SessionChecker;
import com.kravets.hotels.rpnjava.service.IndexPageService;
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
import java.util.List;

@Controller
public class IndexPageController {
    private final IndexPageService indexPageService;
    private final SearchValidatior searchValidatior;
    private final SessionChecker sessionChecker;


    @Autowired
    public IndexPageController(
            IndexPageService indexPageService,
            SearchValidatior searchValidatior,
            SessionChecker sessionChecker
    ) {
        this.indexPageService = indexPageService;
        this.searchValidatior = searchValidatior;
        this.sessionChecker = sessionChecker;
    }


    @GetMapping("/")
    public String indexPage(Model model, HttpServletRequest request) {
        sessionChecker.noRestrictionAccess(model, request);

        ZonedDateTime currentZonedDateTime = CurrentDate.getZonedDateTime();
        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("citiesList", indexPageService.getAllCities());
        model.addAttribute("currentDate", CurrentDate.convertToStringDate(currentZonedDateTime));
        model.addAttribute(
                "currentDatePlusDay",
                CurrentDate.convertToStringDate(currentZonedDateTime.plusDays(1))
        );

        model.addAttribute("templateName", "index");
        return "base";
    }

    @GetMapping("/results")
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
                System.out.println(result.getAllErrors().get(0).toString());
                throw new FormValidationException();
            }
            sessionChecker.noRestrictionAccess(model, request);

            List<RoomEntity> rooms = indexPageService.getEmptyRooms(searchForm);

            model.addAttribute("templateName", "results");
            return "base";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }


    }
}
