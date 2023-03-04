package ru.shipownerproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.services.seamanservice.SeamenService;

@Controller
@RequestMapping("/seaman")
public class SeamenController {

    private final SeamenService seamenService;

    public SeamenController(SeamenService seamenService) {
        this.seamenService = seamenService;
    }

    @GetMapping
    public String showInfoAboutSeaman(Model model) {
        model.addAttribute("seaman", new Seaman());
        model.addAttribute("countries", seamenService.allCountries());
        model.addAttribute("vessels", seamenService.allVessels());
        return "seamen/create-seaman";
    }

    @PostMapping
    public String addNewSeamanToBase(@ModelAttribute("seaman") Seaman seaman) {
        seamenService.addNewSeamanToBase(seaman);
        return "redirect:/vessels/all";
    }

    @DeleteMapping("/delete/{id}")
    public String removeSeamanFromBase(@PathVariable Long id){
        seamenService.removeSeamanFromBase(id);
        return "redirect:vessels/all";
    }

    @GetMapping("/ref/{id}")
    public String refSeamanInBase(@PathVariable Long id, Model model){
        model.addAttribute("seaman", seamenService.seaman(id));
        model.addAttribute("vessels", seamenService.allVessels());
        return "seamen/refactor-seaman";
    }

    @PutMapping("/refactor/{id}")
    public String refactorSeamanInBase(@ModelAttribute("seaman") Seaman seaman, @PathVariable Long id){
        seamenService.refactorSeamanInBase(seaman, id);
        return "redirect:/vessels/all";
    }

}