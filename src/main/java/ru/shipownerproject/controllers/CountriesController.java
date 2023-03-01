package ru.shipownerproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.services.countryservice.CountriesService;

@RestController
@RequestMapping("/country")
public class CountriesController {

    private final CountriesService countriesService;

    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }


    @PostMapping
    public ResponseEntity<String> addNewCountry(@RequestParam String name) {
        return ResponseEntity.ok(countriesService.newCountry(name));
    }

    @GetMapping("/all")
    public ResponseEntity<String> allCountries(){
        return ResponseEntity.ok(countriesService.allCountries());
    }


    @GetMapping("/get")
    public ResponseEntity<String> returnCountry(@RequestParam String name, Model model) {

        return ResponseEntity.ok(countriesService.oneCountry(name));
    }

    @GetMapping("/get/shipowners/{name}")
    public ResponseEntity<String> returnCountryShipOwners(@PathVariable String name, Model model) {

        return ResponseEntity.ok(countriesService.countryShipOwners(name));
    }

    @GetMapping("/get/vessels")
    public ResponseEntity<String> returnCountryVessels(@RequestParam String name, Model model){
        return ResponseEntity.ok(countriesService.countryVessels(name));
    }

    @PutMapping("/refactor/name")
    public ResponseEntity<String> refactorCountryName(@RequestParam String oldCountryName,
                                                      @RequestParam String newCountryName) {
        return ResponseEntity.ok(countriesService.refactorCountryName(oldCountryName, newCountryName));
    }
}