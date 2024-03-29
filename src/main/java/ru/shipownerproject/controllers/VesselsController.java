package ru.shipownerproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.services.vesselservice.VesselsService;
import ru.shipownerproject.utils.$dto.SeamanDTO;
import ru.shipownerproject.utils.$dto.ShipOwnerDTO;
import ru.shipownerproject.utils.$dto.VesselDTO;
import ru.shipownerproject.utils.validators.VesselDTOValidator;

import java.util.List;
import java.util.stream.Collectors;

import static ru.shipownerproject.utils.exceptions.ErrorResponse.notCreatedException;
import static ru.shipownerproject.utils.exceptions.ErrorResponse.notRefactoredException;

@RestController
@RequestMapping("/vessels")
public class VesselsController {

    private final VesselsService vesselsService;

    private final ModelMapper modelMapper;
    private final VesselDTOValidator vesselDTOValidator;

    public VesselsController(VesselsService vesselsService, ModelMapper modelMapper, VesselDTOValidator vesselDTOValidator) {
        this.vesselsService = vesselsService;
        this.modelMapper = modelMapper;
        this.vesselDTOValidator = vesselDTOValidator;
    }


    @PostMapping
    public ResponseEntity<HttpStatus> addNewVessel(@RequestBody VesselDTO vesselDTO,
                                                   BindingResult bindingResult) {
        notCreatedException(bindingResult, vesselDTOValidator, vesselDTO);
        vesselsService.addNewVessel(VesselDTO.convertToVessel(vesselDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{IMO}")
    public ResponseEntity<VesselDTO> getVessel(@PathVariable Integer IMO) {
        return ResponseEntity.ok(VesselDTO.convertToVesselDTO(vesselsService.findVesselByIMO(IMO), modelMapper));
    }

    @GetMapping("/crew/{IMO}")
    public ResponseEntity<List<SeamanDTO>> getCrew(@PathVariable Integer IMO) {
        return ResponseEntity.ok(vesselsService.getInfoAboutCrew(IMO).stream()
                .map(seaman -> SeamanDTO.convertToSeamanDTO(seaman, modelMapper))
                .collect(Collectors.toList()));
    }

    @GetMapping("/shipowner/{IMO}")
    public ResponseEntity<ShipOwnerDTO> getVeselShipOwner(@PathVariable Integer IMO) {
        return ResponseEntity.ok(ShipOwnerDTO.convertToShipOwnerDTO
                (vesselsService.getVesselShipOwner(IMO), modelMapper));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<VesselDTO>> getVesselsByType(@PathVariable String type) {
        return ResponseEntity.ok(vesselsService.allVesselsByType(type).stream()
                .map(vessel -> VesselDTO.convertToVesselDTO(vessel, modelMapper))
                .collect(Collectors.toList()));
    }

    @PutMapping("/refactor")
    public ResponseEntity<HttpStatus> refactorVesselInBase(@RequestBody VesselDTO vesselDTO,
                                                           BindingResult bindingResult) {
        notRefactoredException(bindingResult, vesselDTOValidator, vesselDTO);
        vesselsService.refactorVesselInBase(VesselDTO.convertToVessel(vesselDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{IMO}")
    public ResponseEntity<HttpStatus> removeVesselFromBase(@PathVariable Integer IMO) {
        vesselsService.removeVesselFromBase(IMO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}