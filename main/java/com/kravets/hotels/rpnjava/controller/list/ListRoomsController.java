package com.kravets.hotels.rpnjava.controller.list;

import com.kravets.hotels.rpnjava.form.AddRoomForm;
import com.kravets.hotels.rpnjava.misc.DatabaseServices;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ListRoomsController {
    private final DatabaseServices databaseServices;
    private final SessionCheck sessionCheck;

    @Autowired
    public ListRoomsController(DatabaseServices databaseServices, SessionCheck sessionCheck) {
        this.databaseServices = databaseServices;
        this.sessionCheck = sessionCheck;
    }

    @GetMapping("/list/rooms")
    public String adminRoomsPage(
            Model model,
            @RequestParam(required = false, defaultValue = "0") Long filterHotel,
            @RequestParam(required = false, defaultValue = "0") Long filterCity,
            @RequestParam(required = false, defaultValue = "creationDate") String sortingProperty,
            @RequestParam(required = false, defaultValue = "descending") String sortingDirection,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionCheck.noRestrictionAccess(model, request);

            model.addAttribute("hotelsList", databaseServices.hotel.getAllHotels());
            model.addAttribute("roomsList", databaseServices.room.getRoomsByParameters(filterHotel, filterCity, sortingProperty, sortingDirection));
            model.addAttribute("citiesList", databaseServices.cities.getAllCities());
            model.addAttribute("filterHotel", filterHotel);
            model.addAttribute("filterCity", filterCity);
            model.addAttribute("sortingProperty", sortingProperty);
            model.addAttribute("sortingDirection", sortingDirection);

            model.addAttribute("templateName", "rooms");
            model.addAttribute("templateType", "list");
            return "base";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }
}
