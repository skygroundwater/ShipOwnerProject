package ru.shipownerproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.services.shipsownerservice.ShipsOwnerService;

@RestController
@RequestMapping("/shipowner")
public class ShipsOwnerController {

    private final ShipsOwnerService shipsOwnerService;

    public ShipsOwnerController(ShipsOwnerService shipsOwnerService) {
        this.shipsOwnerService = shipsOwnerService;
    }

    @GetMapping("/get")
    public ResponseEntity<String> shipOwner(@RequestParam String name, Model model) {
        return ResponseEntity.ok(shipsOwnerService.shipOwner(name));
    }

    @GetMapping("/get/vessels")
    public ResponseEntity<String> shipOwnerVessels(@RequestParam String name) {
        return ResponseEntity.ok(shipsOwnerService.shipOwnerVessels(name));
    }

    @PostMapping
    public ResponseEntity<String> addNewShipOwner(@RequestParam String countryName,
                                                  @RequestParam String name,
                                                  @RequestParam String description) {
        return ResponseEntity.ok(shipsOwnerService
                .addNewShipOwner(countryName, name, description));
    }

    @PutMapping("/refactor/country")
    public ResponseEntity<String> refactorCountryForShipOwner(@RequestParam String shipOwner,
                                                              @RequestParam String countryName) {
        return ResponseEntity.ok(shipsOwnerService.setCountryForShipOwner(shipOwner, countryName));
    }

    @PutMapping("/refactor/name")
    public ResponseEntity<String> refactorNameForShipOwner(@RequestParam String oldShipOwnerName,
                                                           @RequestParam String newShipOwnerName) {
        return ResponseEntity.ok(shipsOwnerService.setNameForShipOwner(oldShipOwnerName, newShipOwnerName));
    }

    @DeleteMapping("/delete/name")
    public ResponseEntity<String> deleteShipOwnerFromBase(@RequestParam String name) {
        return ResponseEntity.ok(shipsOwnerService.removeFromBaseShipOwner(name));
    }
}