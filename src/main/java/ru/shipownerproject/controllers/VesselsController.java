package ru.shipownerproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.services.vesselservice.VesselsService;
import ru.shipownerproject.services.vesselservice.typeservice.VesselTypesService;

@RestController
@RequestMapping("/vessel")
public class VesselsController {

    private final VesselsService vesselsService;

    private final VesselTypesService vesselTypesService;

    public VesselsController(VesselsService vesselsService, VesselTypesService vesselTypesService) {
        this.vesselsService = vesselsService;
        this.vesselTypesService = vesselTypesService;
    }

    @PostMapping("/addallvesseltypes")
    public ResponseEntity<String> addAllVesselTypes(){
        return ResponseEntity.ok(vesselTypesService.addAllVesselTypeToBase());
    }

    @PostMapping
    public ResponseEntity<String> addNewVessel(@RequestParam String vesselName,
                                               @RequestParam String IMO,
                                               @RequestParam String country,
                                               @RequestParam String shipOwnerName,
                                               @RequestParam Short typeId) {
        return ResponseEntity.ok(vesselsService.addNewVessel(
                vesselName, country, shipOwnerName, IMO, typeId));

    }

    @GetMapping("/get")
    public ResponseEntity<String> getVessel(@RequestParam String IMO) {
        return ResponseEntity.ok(vesselsService.vessel(IMO));
    }

    @GetMapping("/crew")
    public ResponseEntity<String> getCrew(@RequestParam String IMO){
        return ResponseEntity.ok(vesselsService.getInfoAboutCrew(IMO));
    }

    @GetMapping("/type")
    public ResponseEntity<String> getVesselsByType(@RequestParam Short id){
        return ResponseEntity.ok(vesselsService.allVesselsByType(id));
    }

    @PutMapping("/refactor")
    public ResponseEntity<String> refactorVesselInBase(@RequestParam String IMO,
                                                       @RequestParam String newName,
                                                       @RequestParam String newCountry, Short newVesselTypeId,
                                                       @RequestParam String newShipOwnerName){
        return ResponseEntity.ok(vesselsService.refactorVesselInBase(IMO, newName, newCountry,
                newVesselTypeId, newShipOwnerName));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeVesselFromBase(String IMO) {
        return ResponseEntity.ok(vesselsService.removeVesselFromBase(String.valueOf(IMO)));
    }
}
