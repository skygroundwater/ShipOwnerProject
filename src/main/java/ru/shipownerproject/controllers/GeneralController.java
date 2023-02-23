package ru.shipownerproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/general")
public class GeneralController {

    @GetMapping("/greeting")
    public String startPageMethod(Model model) {

        model.addAttribute("greeting", "Welcome to the Ship's Owners Project");
        model.addAttribute("choose", "Choose any shipowner to start:");

        return "general-page";
    }



}
