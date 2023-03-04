package ru.shipownerproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.services.vesselservice.VesselsService;
import ru.shipownerproject.services.vesselservice.typeservice.VesselTypesService;

@Controller
@RequestMapping("/vessels")
public class VesselsController {

    private final VesselsService vesselsService;

    private final VesselTypesService vesselTypesService;

    public VesselsController(VesselsService vesselsService, VesselTypesService vesselTypesService) {
        this.vesselsService = vesselsService;
        this.vesselTypesService = vesselTypesService;
    }

    @PostMapping("/addallvesseltypes")
    public ResponseEntity<String> addAllVesselTypes() {
        return ResponseEntity.ok(vesselTypesService.addAllVesselTypeToBase());
    }

    @GetMapping("/type")
    public ResponseEntity<String> getVesselsByType(@RequestParam Byte id) {
        return ResponseEntity.ok(vesselsService.allVesselsByType(id));
    }

    @GetMapping("/all")
    public String vessels(Model model) {
        model.addAttribute("vessels", vesselsService.allVessels());
        return "vessels/vessels";
    }

    @GetMapping
    public String createNewVessel(@ModelAttribute("vessel") Vessel vessel, Model model) {
        model.addAttribute("types", vesselTypesService.allTypes());
        model.addAttribute("countries", vesselsService.allCountries());
        model.addAttribute("shipowners", vesselsService.allShipOwners());
        return "vessels/create-vessel";
    }

    @PostMapping
    public String addNewVessel(@ModelAttribute("vessel") Vessel vessel, Model model) {
        vesselsService.addNewVessel(vessel);
        return "redirect:/vessels/all";
    }

    @GetMapping("/one/{id}")
    public String getVessel(@PathVariable Long id, Model model) {
        model.addAttribute("vessel", vesselsService.vessel(id));
        return "vessels/vessel";
    }

    @GetMapping("/crew/{id}")
    public String getCrew(@PathVariable Long id, Model model) {
        model.addAttribute("crew", vesselsService.crew(id));
        return "vessels/vessel-crew";
    }

    @GetMapping("/ref/{id}")
    public String refVesselPage(@PathVariable Long id, Model model) {
        model.addAttribute("vessel", vesselsService.vessel(id));
        model.addAttribute("types", vesselTypesService.allTypes());
        model.addAttribute("countries", vesselsService.allCountries());
        model.addAttribute("shipowners", vesselsService.allShipOwners());
        return "vessels/refactor-vessel";
    }

    @PutMapping("/refactor/{id}")
    public String refactorVesselInBase(@ModelAttribute("vessel") Vessel vessel, @PathVariable Long id) {
        vesselsService.refactorVesselInBase(vessel, id);
        return "redirect:/vessels/all";
    }

    @DeleteMapping("/remove/{id}")
    public String removeVesselFromBase(@ModelAttribute("vessel") Vessel vessel) {
        vesselsService.removeVesselFromBase(vessel);
        return "redirect:/vessels/all";
    }
}