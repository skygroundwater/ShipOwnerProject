package ru.shipownerproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.services.shipsownerservice.ShipOwnersService;

@RestController
@RequestMapping("/shipowner")
public class ShipOwnersController {

    private final ShipOwnersService shipOwnersService;

    public ShipOwnersController(ShipOwnersService shipOwnersService) {
        this.shipOwnersService = shipOwnersService;
    }

    @GetMapping("/get")
    public ResponseEntity<String> shipOwner(@RequestParam String name) {
        return ResponseEntity.ok(shipOwnersService.shipOwner(name));
    }

    @GetMapping("/get/vessels")
    public ResponseEntity<String> shipOwnerVessels(@RequestParam String name) {
        return ResponseEntity.ok(shipOwnersService.shipOwnerVessels(name));
    }

    @PostMapping
    public ResponseEntity<String> addNewShipOwner(@RequestParam String countryName,
                                                  @RequestParam String name,
                                                  @RequestParam String description) {
        return ResponseEntity.ok(shipOwnersService
                .addNewShipOwner(countryName, name, description));
    }

    @PutMapping("/refactor/country")
    public ResponseEntity<String> refactorCountryForShipOwner(@RequestParam String shipOwner,
                                                              @RequestParam String countryName) {
        return ResponseEntity.ok(shipOwnersService.refactorCountryForShipOwner(shipOwner, countryName));
    }

    @PutMapping("/refactor/name")
    public ResponseEntity<String> refactorNameForShipOwner(@RequestParam String oldShipOwnerName,
                                                           @RequestParam String newShipOwnerName) {
        return ResponseEntity.ok(shipOwnersService.setNameForShipOwner(oldShipOwnerName, newShipOwnerName));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteShipOwnerFromBase(@RequestParam String name) {
        return ResponseEntity.ok(shipOwnersService.removeFromBaseShipOwner(name));
    }
}