package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.misc.LoggedInChecker;
import com.kravets.hotels.rpnjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {
    private final UserService userService;
    private final LoggedInChecker loggedInChecker;

    @Autowired
    public RegisterController(UserService userService, LoggedInChecker loggedInChecker) {
        this.userService = userService;
        this.loggedInChecker = loggedInChecker;
    }

    @GetMapping("/register")
    public String registerPage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            model = loggedInChecker.loggedOutAccess(model, request);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }

        if (!model.containsAttribute("userEntity")) {
            model.addAttribute("userEntity", new UserEntity());
        }

        return "register";
    }

    @PostMapping("/register")
    public String registerAction(Model model,
                                 @ModelAttribute UserEntity userEntity,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        try {
            loggedInChecker.loggedOutAccess(model, request);
            userService.registerUser(userEntity);
            UserEntity newUserEntity = new UserEntity(userEntity.getLogin());
            redirectAttributes.addFlashAttribute("userEntity", newUserEntity);
            redirectAttributes.addFlashAttribute("successMessage", "Акаўнт паспяхова створаны!");
            return "redirect:/login";
        } catch (Exception e) {
            userEntity.setPasswordHash("");
            redirectAttributes.addFlashAttribute("userEntity", userEntity);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }
}
