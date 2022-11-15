package com.kravets.hotels.rpnjava.controller.admin;

import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.form.AddRoomForm;
import com.kravets.hotels.rpnjava.form.EditRoomForm;
import com.kravets.hotels.rpnjava.misc.SessionChecker;
import com.kravets.hotels.rpnjava.service.AdminService;
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
    private final AdminService adminService;
    private final SessionChecker sessionChecker;
    private final AddRoomValidator addRoomValidator;
    private final EditRoomValidator editRoomValidator;

    @Autowired
    public AdminRoomsController(
            AdminService adminService,
            SessionChecker sessionChecker,
            AddRoomValidator addRoomValidator,
            EditRoomValidator editRoomValidator
    ) {
        this.adminService = adminService;
        this.sessionChecker = sessionChecker;
        this.addRoomValidator = addRoomValidator;
        this.editRoomValidator = editRoomValidator;
    }

    @GetMapping("/admin/rooms")
    public String adminRoomsPage(
            Model model,
            @RequestParam(required = false) Long hotel,
            @RequestParam(required = false) Long filterHotel,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionChecker.adminAccess(model, request);

            model.addAttribute("addRoomForm", new AddRoomForm());
            model.addAttribute("hotelsList", adminService.getAllHotels());
            model.addAttribute("roomsList", adminService.getAllRooms());
            model.addAttribute("pickHotelId", hotel);
            model.addAttribute("filterHotel", filterHotel);

            model.addAttribute("templateName", "admin/rooms");
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
            sessionChecker.adminAccess(model, request);

            adminService.addRoom(addRoomForm);

            redirectAttributes.addFlashAttribute("successMessage", "Новы пакой паспяхова дабаўлены");
            return "redirect:/admin/rooms";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/rooms";
        }
    }

    @PostMapping("/admin/rooms/remove")
    public String removeRoomAction(
            Model model,
            @RequestParam Long id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            sessionChecker.adminAccess(model, request);

            adminService.removeRoom(id);

            redirectAttributes.addFlashAttribute("successMessage", "Пакой паспяхова выдалены");
            return "redirect:/admin/rooms";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/rooms";
        }
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
            sessionChecker.adminAccess(model, request);

            adminService.editRoom(editRoomForm);

            redirectAttributes.addFlashAttribute("successMessage", "Інфармацыя пра пакой паспяхова зменена");
            return "redirect:/admin/rooms";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/rooms";
        }
    }
}
