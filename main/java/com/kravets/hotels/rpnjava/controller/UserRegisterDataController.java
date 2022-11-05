package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.model.UserRegisterData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserRegisterDataController {

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("UserRegisterData", new UserRegisterData());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute UserRegisterData UserRegisterData, Model model) {
        model.addAttribute("UserRegisterData", UserRegisterData);
        return "result";
    }

}