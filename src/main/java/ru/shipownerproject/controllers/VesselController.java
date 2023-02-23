package ru.shipownerproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.services.vesselservice.VesselService;

@RestController
@RequestMapping("/vessel")
public class VesselController {

    private final VesselService vesselService;

    public VesselController(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    @PostMapping
    public ResponseEntity<String> addNewVessel(@RequestParam String vesselName,
                                               @RequestParam String IMO,
                                               @RequestParam String shipOwnerName) {
        return ResponseEntity.ok(vesselService.addNewVessel(
                vesselName, shipOwnerName, IMO));

    }

    @GetMapping("/get")
    public ResponseEntity<String> getVessel(@RequestParam String name) {
        return ResponseEntity.ok(vesselService.vessel(name));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeVesselFromBase(String IMO) {
        return ResponseEntity.ok(vesselService.removeVesselFromBase(String.valueOf(IMO)));
    }


}
