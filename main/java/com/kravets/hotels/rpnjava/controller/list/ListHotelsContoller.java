package com.kravets.hotels.rpnjava.controller.list;

import com.kravets.hotels.rpnjava.entity.HotelEntity;
import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ListHotelsContoller {
    private final Services services;
    private final SessionCheck sessionCheck;

    @Autowired
    public ListHotelsContoller(Services services, SessionCheck sessionCheck) {
        this.services = services;
        this.sessionCheck = sessionCheck;
    }

    @GetMapping("/list/hotels")
    public String listHotelsPage(
            Model model,
            @RequestParam(required = false, defaultValue = "0") Long filterCity,
            @RequestParam(required = false, defaultValue = "creationDate") String sortingProperty,
            @RequestParam(required = false, defaultValue = "descending") String sortingDirection,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionCheck.noRestrictionAccess(model, request);

            List<HotelEntity> hotelsList = services.hotel.getHotelsByParameters(filterCity, sortingProperty, sortingDirection);

            model.addAttribute("citiesList", services.cities.getAllCities());
            model.addAttribute("hotelsList", hotelsList);
            model.addAttribute("roomsCountList", services.room.getRoomsCountListByHotelsList(hotelsList));
            model.addAttribute("filterCity", filterCity);
            model.addAttribute("sortingProperty", sortingProperty);
            model.addAttribute("sortingDirection", sortingDirection);

            model.addAttribute("templateName", "hotels");
            model.addAttribute("templateType", "list");
            return "base";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }
}
