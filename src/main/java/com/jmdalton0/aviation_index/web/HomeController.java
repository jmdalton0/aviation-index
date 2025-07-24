package com.jmdalton0.aviation_index.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller for the home page.
 */
@Controller
@RequestMapping
public class HomeController {

    /**
     * An endpoint to view the home page.
     * @param model view model.
     * @return home page view.
     */
    @GetMapping
    public String index(Model model) {
        model.addAttribute("title", "Aviation Index");
        return "home";
    }

}
