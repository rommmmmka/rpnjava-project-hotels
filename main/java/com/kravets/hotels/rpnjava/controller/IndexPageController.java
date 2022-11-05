package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.model.UserRegisterData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexPageController {

    @GetMapping("/")
    public String loginForm() {
        return "index";
    }

}