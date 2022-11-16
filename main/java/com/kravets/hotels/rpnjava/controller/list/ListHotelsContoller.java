package com.kravets.hotels.rpnjava.controller.list;

import com.kravets.hotels.rpnjava.form.AddHotelForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ListHotelsContoller {

//    @GetMapping("/list/hotels")
//    public String listHotelsPage(
//            Model model,
//            @RequestParam(required = false, defaultValue = "0") Long filterCity,
//            @RequestParam(required = false, defaultValue = "creationDate") String sortingProperty,
//            @RequestParam(required = false, defaultValue = "descending") String sortingDirection,
//            HttpServletRequest request,
//            RedirectAttributes redirectAttributes
//    ) {
//        try {
//            sessionChecker.adminAccess(model, request);
//
//            model.addAttribute("addHotelForm", new AddHotelForm());
//            model.addAttribute("citiesList", adminService.getAllCities());
//
//            model.addAttribute("hotelsList", admi.getAllHotels(filterCity, sortingProperty, sortingDirection));
//            model.addAttribute("filterCity", filterCity);
//            model.addAttribute("sortingProperty", sortingProperty);
//            model.addAttribute("sortingDirection", sortingDirection);
//
//            model.addAttribute("templateName", "hotels");
//            model.addAttribute("templateType", "admin");
//            return "base";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//            return "redirect:/";
//        }
//    }
}
