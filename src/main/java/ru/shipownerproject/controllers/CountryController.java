package ru.shipownerproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.services.countryservice.CountryService;

@RestController
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/get")
    public ResponseEntity<String> returnCountry(@RequestParam String name) {
        return ResponseEntity.ok(countryService.oneCountry(name));
    }

    @GetMapping("/get/shipowners")
    public ResponseEntity<String> returnCountryShipOwners(@RequestParam String name) {
        return ResponseEntity.ok(countryService.countryShipOwners(name));
    }

    @PostMapping
    public ResponseEntity<Void> addNewCountry(@RequestParam String name) {
        countryService.newCountry(name);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/refactor/name")
    public ResponseEntity<String> refactorCountryName(@RequestParam String oldCountryName,
                                                      @RequestParam String newCountryName) {
        return ResponseEntity.ok(countryService.refactorCountryName(oldCountryName, newCountryName));
    }
}