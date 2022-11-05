package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.model.UserRegisterData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserLoginDataController {

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("userRegistrationData", new UserRegisterData());
        return "register";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute UserRegisterData userRegistrationData, Model model) {
        model.addAttribute("userRegistrationData", userRegistrationData);
        return "result";
    }

}