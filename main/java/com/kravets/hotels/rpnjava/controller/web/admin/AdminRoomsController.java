package com.kravets.hotels.rpnjava.controller.web.admin;

import com.kravets.hotels.rpnjava.data.form.AddRoomForm;
import com.kravets.hotels.rpnjava.data.form.EditRoomForm;
import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.misc.Services;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import com.kravets.hotels.rpnjava.validator.AddRoomValidator;
import com.kravets.hotels.rpnjava.validator.EditRoomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminRoomsController {
    private final Services services;
    private final SessionCheck sessionCheck;
    private final AddRoomValidator addRoomValidator;
    private final EditRoomValidator editRoomValidator;

    @Autowired
    public AdminRoomsController(
            Services services,
            SessionCheck sessionCheck,
            AddRoomValidator addRoomValidator,
            EditRoomValidator editRoomValidator
    ) {
        this.services = services;
        this.sessionCheck = sessionCheck;
        this.addRoomValidator = addRoomValidator;
        this.editRoomValidator = editRoomValidator;
    }

    @GetMapping("/admin/rooms")
    public String adminRoomsPage(
            Model model,
            @RequestParam(required = false) Long hotel,
            @RequestParam(required = false, defaultValue = "0") Long filterHotel,
            @RequestParam(required = false, defaultValue = "0") Long filterCity,
            @RequestParam(required = false, defaultValue = "creationDate") String sortingProperty,
            @RequestParam(required = false, defaultValue = "descending") String sortingDirection,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionCheck.adminAccess(model, request);

            model.addAttribute("addRoomForm", new AddRoomForm());
            model.addAttribute("pickHotelId", hotel);
            model.addAttribute("hotelsList", services.hotel.getAllHotels());
            model.addAttribute("roomsList", services.db.getRoomsByParameters(filterHotel, filterCity, sortingProperty, sortingDirection));
            model.addAttribute("citiesList", services.cities.getAllCities());
            model.addAttribute("filterHotel", filterHotel);
            model.addAttribute("filterCity", filterCity);
            model.addAttribute("sortingProperty", sortingProperty);
            model.addAttribute("sortingDirection", sortingDirection);

            model.addAttribute("templateName", "rooms");
            model.addAttribute("templateType", "admin");
            return "base";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/admin/rooms/create")
    public String addRoomAction(
            Model model,
            @ModelAttribute AddRoomForm addRoomForm,
            BindingResult result,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            addRoomValidator.validate(addRoomForm, result);
            if (result.hasErrors()) {
                throw new FormValidationException();
            }
            sessionCheck.adminAccess(model, request);

            services.db.addRoom(addRoomForm);

            redirectAttributes.addFlashAttribute("successMessage", "Новы пакой паспяхова дабаўлены");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/rooms";
    }

    @PostMapping("/admin/rooms/remove")
    public String removeRoomAction(
            Model model,
            @RequestParam Long id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionCheck.adminAccess(model, request);

            services.room.removeRoom(id);

            redirectAttributes.addFlashAttribute("successMessage", "Пакой паспяхова выдалены");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/rooms";
    }

    @PostMapping("/admin/rooms/edit")
    public String editRoomAction(
            Model model,
            @ModelAttribute EditRoomForm editRoomForm,
            BindingResult result,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            editRoomValidator.validate(editRoomForm, result);
            if (result.hasErrors()) {
                throw new FormValidationException();
            }
            sessionCheck.adminAccess(model, request);

            services.room.editRoom(editRoomForm);

            redirectAttributes.addFlashAttribute("successMessage", "Інфармацыя пра пакой паспяхова зменена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/rooms";
    }
}
