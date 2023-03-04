package ru.shipownerproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.services.countryservice.CountriesService;

@Controller
@RequestMapping("/countries")
public class CountriesController {

    private final CountriesService countriesService;

    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @ModelAttribute("header")
    public String title(){
        return "Ship Owner Project";
    }

    @PostMapping
    public String addNewCountry(@ModelAttribute("country") Country country, Model model) {
        countriesService.newCountry(country);
        return "redirect:/general";
    }

    @GetMapping("/add")
    public String startCreateNewCountry(@ModelAttribute("country") Country country) {
        return "countries/create-country";
    }

    @GetMapping
    public String allCountries(Model model){
        model.addAttribute("countries", countriesService.allCountries());
        return "countries/countries";
    }

    @GetMapping("/one/{id}")
    public String aboutCountry(@PathVariable Short id, Model model){
        model.addAttribute("country", countriesService.oneCountry(id));
        return "countries/country";
    }

    @GetMapping("/shipowners/{id}")
    public String returnCountryShipOwners(@PathVariable Short id, Model model) {
        model.addAttribute("shipowners", countriesService.countryShipOwners(id));
        return "countries/country-shipowners";
    }

    @GetMapping("/vessels/{id}")
    public String returnCountryVessels(@PathVariable Short id, Model model){
        model.addAttribute("vessels", countriesService.countryVessels(id));
        return "countries/country-vessels";
    }

    @GetMapping("/ref/{id}")
    public String startRefactoringCountry(@PathVariable Short id, Model model){
        model.addAttribute("country", countriesService.oneCountry(id));
        return "countries/refactor-country";
    }

    @PutMapping("/refactor/{id}")
    public String refactorCountryName(@ModelAttribute("country") Country country,
                                      @PathVariable Short id) {
        countriesService.refactorCountryName(id, country.getName());
        return "redirect:/country/all";
    }
}