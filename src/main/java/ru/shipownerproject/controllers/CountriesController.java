package ru.shipownerproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.services.countryservice.CountriesService;

@Controller
@RequestMapping("/country")
public class CountriesController {

    private final CountriesService countriesService;

    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @ModelAttribute("header")
    public String title(){
        return "Ship Owner Project";
    }


    @GetMapping("/add")
    public String startCreateNewCountry(@ModelAttribute("country") Country country){

        return "countries/create-country";
    }

    @PostMapping
    public String addNewCountry(@ModelAttribute("country") Country country, Model model) {

        countriesService.newCountry(country);

        return "redirect:/general";
    }

    @GetMapping("/all")
    public String allCountries(Model model){

        model.addAttribute("countries", countriesService.allCountries());

        return "countries/all-countries";
    }


    @GetMapping("/get")
    public ResponseEntity<String> returnCountry(@RequestParam String name, Model model) {

        return ResponseEntity.ok(countriesService.oneCountry(name));
    }

    @GetMapping("/get/shipowners/{name}")
    public String returnCountryShipOwners(@PathVariable String name, Model model) {
        model.addAttribute("shipowners", countriesService.countryShipOwners(name));
        return "countries/all-shipowners-for-country";
    }

    @GetMapping("/get/vessels/{name}")
    public String returnCountryVessels(@PathVariable String name, Model model){
        model.addAttribute("vessels", countriesService.countryVessels(name));
        return "countries/all-vessels-for-country";
    }

    @PutMapping("/refactor/name")
    public ResponseEntity<String> refactorCountryName(@RequestParam String oldCountryName,
                                                      @RequestParam String newCountryName) {
        return ResponseEntity.ok(countriesService.refactorCountryName(oldCountryName, newCountryName));
    }
}