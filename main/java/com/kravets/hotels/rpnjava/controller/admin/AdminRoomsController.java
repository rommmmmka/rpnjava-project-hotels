package com.kravets.hotels.rpnjava.controller.admin;

import com.kravets.hotels.rpnjava.form.AddRoomForm;
import com.kravets.hotels.rpnjava.misc.SessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminRoomsController {
    private final SessionChecker sessionChecker;

    @Autowired
    public AdminRoomsController(SessionChecker sessionChecker) {
        this.sessionChecker = sessionChecker;
    }

    @GetMapping("/admin/rooms")
    public String adminRoomsPage(
            Model model,
            @RequestParam Long hotel,
            @RequestParam Long filterHotel,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        try {
            sessionChecker.adminAccess(model, request);

            model.addAttribute("addRoomForm", new AddRoomForm());
            model.addAttribute("pickHotel", hotel);
            model.addAttribute("filterHotel", filterHotel);


            model.addAttribute("templateName", "admin/rooms");
            return "base";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }

}
