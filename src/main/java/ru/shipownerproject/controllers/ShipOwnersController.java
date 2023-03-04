package ru.shipownerproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.services.shipsownerservice.ShipOwnersService;

@Controller
@RequestMapping("/shipowners")
public class ShipOwnersController {

    private final ShipOwnersService shipOwnersService;

    public ShipOwnersController(ShipOwnersService shipOwnersService) {
        this.shipOwnersService = shipOwnersService;
    }

    @GetMapping("/add")
    public String createShipOwnerPage(Model model){
        model.addAttribute("shipowner", new ShipOwner());
        model.addAttribute("countries", shipOwnersService.allCountries());
        return "shipowners/create-shipowner";
    }

    @PostMapping
    public String addNewShipOwner(@ModelAttribute("shipowner") ShipOwner shipOwner) {
        shipOwnersService.addNewShipOwner(shipOwner);
        return "redirect:/shipowners";
    }

    @GetMapping
    public String allShipOwners(Model model){
        model.addAttribute("shipowners", shipOwnersService.allShipOwners());
        return "shipowners/shipowners";
    }

    @GetMapping("/one/{id}")
    public String shipOwner(@PathVariable Long id, Model model) {
        model.addAttribute("shipowner", shipOwnersService.shipOwner(id));
        return "shipowners/shipowner";
    }

    @GetMapping("/vessels/{id}")
    public String shipOwnerVessels(@PathVariable Long id, Model model) {
        model.addAttribute("vessels", shipOwnersService.shipOwnerVessels(id));
        return "shipowners/shipowners-vessels";
    }

    @GetMapping("/seamen/{id}")
    public String shipOwnerSeamen(@PathVariable Long id, Model model){
        model.addAttribute("seamen", shipOwnersService.shipOwnerSeamen(id));
    return "shipowners/shipowner-seamen";
    }

    @GetMapping("/ref/{id}")
    public String refShipOwner(@PathVariable Long id, Model model) {
        model.addAttribute("shipowner", shipOwnersService.shipOwner(id));
        model.addAttribute("countries", shipOwnersService.allCountries());
        return "shipowners/refactor-shipowner";
    }

    @PutMapping("/refactor/{id}")
    public String refactorShipOwner(@ModelAttribute("shipowner") ShipOwner shipOwner,
                                    @PathVariable Long id) {
        System.out.println(shipOwner.getCountry().getName());
        shipOwnersService.refactorShipOwner(shipOwner, id);
        return "redirect:/shipowners";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteShipOwnerFromBase(@PathVariable Long id) {
         shipOwnersService.removeFromBaseShipOwner(id);
         return "redirect:/shipowners";
    }
}