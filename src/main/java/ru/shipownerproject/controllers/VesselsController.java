package ru.shipownerproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.exceptions.ErrorResponse;
import ru.shipownerproject.exceptions.NotFoundInBaseException;
import ru.shipownerproject.models.$dto.SeamanDTO;
import ru.shipownerproject.models.$dto.VesselDTO;
import ru.shipownerproject.services.vesselservice.VesselsService;
import ru.shipownerproject.services.vesselservice.typeservice.VesselTypesService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/vessels")
public class VesselsController {
    private final VesselsService vesselsService;
    private final VesselTypesService vesselTypesService;
    private final ModelMapper modelMapper;
    public VesselsController(VesselsService vesselsService, VesselTypesService vesselTypesService, ModelMapper modelMapper) {
        this.vesselsService = vesselsService;
        this.vesselTypesService = vesselTypesService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/addallvesseltypes")
    public ResponseEntity<HttpStatus> addAllVesselTypes(){
        vesselTypesService.addAllVesselTypeToBase();
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewVessel(@RequestBody VesselDTO vesselDTO) {
        vesselsService.addNewVessel(VesselDTO.convertToVessel(vesselDTO, modelMapper), vesselDTO.getIMO());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getVessel(@PathVariable Long id) {
        return ResponseEntity.ok(VesselDTO.convertToVesselDTO(vesselsService.vessel(id), modelMapper));
    }

    @GetMapping("/crew/{id}")
    public ResponseEntity<Object> getCrew(@PathVariable Long id){
        return  ResponseEntity.ok(vesselsService.getInfoAboutCrew(id).stream()
                .map(seaman -> SeamanDTO.convertToSeamanDTO(seaman, modelMapper))
                .collect(Collectors.toList()));
    }

    @GetMapping("/type/{id}")
    public ResponseEntity<Object> getVesselsByType(@PathVariable Short id){
        return ResponseEntity.ok(vesselsService.allVesselsByType(id).stream()
                .map(vessel -> VesselDTO.convertToVesselDTO(vessel, modelMapper))
                .collect(Collectors.toList()));
    }

    @PutMapping("/refactor/{id}")
    public ResponseEntity<HttpStatus> refactorVesselInBase(@RequestBody VesselDTO vesselDTO,
                                                       @PathVariable Long id){
        vesselsService.refactorVesselInBase(id, VesselDTO.convertToVessel(vesselDTO, modelMapper));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<HttpStatus> removeVesselFromBase(@PathVariable Long id) {
        vesselsService.removeVesselFromBase(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(AlreadyAddedToBaseException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(NotFoundInBaseException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

}