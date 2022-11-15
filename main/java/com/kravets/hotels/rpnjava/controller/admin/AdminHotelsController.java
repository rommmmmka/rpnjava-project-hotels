package com.kravets.hotels.rpnjava.controller.admin;

import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.form.AddHotelForm;
import com.kravets.hotels.rpnjava.form.EditHotelForm;
import com.kravets.hotels.rpnjava.misc.SessionChecker;
import com.kravets.hotels.rpnjava.service.AdminService;
import com.kravets.hotels.rpnjava.validator.AddHotelValidator;
import com.kravets.hotels.rpnjava.validator.EditHotelValidator;
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
public class AdminHotelsController {
    private final AdminService adminService;
    private final SessionChecker sessionChecker;
    private final AddHotelValidator addHotelValidator;
    private final EditHotelValidator editHotelValidator;

    @Autowired
    public AdminHotelsController(
            AdminService adminService,
            SessionChecker sessionChecker,
            AddHotelValidator addHotelValidator,
            EditHotelValidator editHotelValidator
    ) {
        this.adminService = adminService;
        this.sessionChecker = sessionChecker;
        this.addHotelValidator = addHotelValidator;
        this.editHotelValidator = editHotelValidator;
    }

    @GetMapping("/admin/hotels")
    public String adminHotelsPage(
            Model model,
            @RequestParam(required = false, defaultValue = "0") Long filterCity,
            @RequestParam(required = false, defaultValue = "creationDate") String sortingProperty,
            @RequestParam(required = false, defaultValue = "descending") String sortingDirection,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionChecker.adminAccess(model, request);

            model.addAttribute("addHotelForm", new AddHotelForm());
            model.addAttribute("citiesList", adminService.getAllCities());

            model.addAttribute("hotelsList", adminService.getAllHotels(filterCity, sortingProperty, sortingDirection));
            model.addAttribute("filterCity", filterCity);
            model.addAttribute("sortingProperty", sortingProperty);
            model.addAttribute("sortingDirection", sortingDirection);

            model.addAttribute("templateName", "hotels");
            model.addAttribute("templateType", "admin");
            return "base";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/admin/hotels/create")
    public String addHotelAction(
            Model model,
            @ModelAttribute AddHotelForm addHotelForm,
            BindingResult result,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            addHotelValidator.validate(addHotelForm, result);
            if (result.hasErrors()) {
                throw new FormValidationException();
            }
            sessionChecker.adminAccess(model, request);

            adminService.addHotel(addHotelForm);

            redirectAttributes.addFlashAttribute("successMessage", "Новы гатэль паспяхова дабаўлены");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/hotels";
    }

    @PostMapping("/admin/hotels/remove")
    public String removeHotelAction(
            Model model,
            @RequestParam Long id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionChecker.adminAccess(model, request);

            adminService.removeHotel(id);

            redirectAttributes.addFlashAttribute("successMessage", "Гатэль паспяхова выдалены");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/hotels";
    }

    @PostMapping("/admin/hotels/edit")
    public String editHotelAction(
            Model model,
            @ModelAttribute EditHotelForm editHotelForm,
            BindingResult result,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            editHotelValidator.validate(editHotelForm, result);
            if (result.hasErrors()) {
                throw new FormValidationException();
            }
            sessionChecker.adminAccess(model, request);

            adminService.editHotel(editHotelForm);

            redirectAttributes.addFlashAttribute("successMessage", "Інфармацыя пра гатэль паспяхова зменена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/hotels";
    }
}
