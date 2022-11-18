package com.kravets.hotels.rpnjava.controller;

import com.kravets.hotels.rpnjava.misc.DateUtils;
import com.kravets.hotels.rpnjava.misc.SessionCheck;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller
public class EasterEggsController {
    private final SessionCheck sessionCheck;

    @Autowired
    public EasterEggsController(SessionCheck sessionCheck) {
        this.sessionCheck = sessionCheck;
    }

    @GetMapping("/privacypolicy")
    public String memesPage(Model model, HttpServletRequest request) {
        sessionCheck.noRestrictionAccess(model, request);

        Random rand = new Random();
        model.addAttribute("memeNumber", rand.nextInt(11) + 1);

        model.addAttribute("templateName", "privacypolicy");
        return "base";
    }

    @GetMapping("/termsofuse")
    public String anecdotesPage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        sessionCheck.noRestrictionAccess(model, request);

        try {
            Document document = Jsoup.connect("https://povodvypit.wordpress.com/" + DateUtils.getStringShortDate()).get();
            Elements elements = document.select(
                    "div.entry-content > h3:not(:contains(Именины)), div.entry-content >  h3:not(:contains(Именины)) + p"
            );
            elements.select("a").unwrap();
            String termsOfUseContent = elements.outerHtml();
            model.addAttribute("termsOfUseContent", termsOfUseContent);

            model.addAttribute("templateName", "termsofuse");
            return "base";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }


    }
}
