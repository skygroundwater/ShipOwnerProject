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
import ru.shipownerproject.utils.$dto.validators.VesselDTOValidator;
import ru.shipownerproject.utils.exceptions.*;

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
    public ResponseEntity<HttpStatus> addNewVessel(@RequestBody VesselDTO vesselDTO, BindingResult bindingResult) {
        notCreatedException(bindingResult, vesselDTOValidator, new StringBuilder(), vesselDTO);
        vesselsService.addNewVessel(VesselDTO.convertToVessel(vesselDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VesselDTO> getVessel(@PathVariable Long id) {
        return ResponseEntity.ok(VesselDTO.convertToVesselDTO(vesselsService.vessel(id), modelMapper));
    }

    @GetMapping("/crew/{id}")
    public ResponseEntity<List<SeamanDTO>> getCrew(@PathVariable Long id){
        return  ResponseEntity.ok(vesselsService.getInfoAboutCrew(id).stream()
                .map(seaman -> SeamanDTO.convertToSeamanDTO(seaman, modelMapper))
                .collect(Collectors.toList()));
    }

    @GetMapping("/shipowner/{id}")
    public ResponseEntity<ShipOwnerDTO> getVeselShipOwner(@PathVariable Long id){
        return ResponseEntity.ok(ShipOwnerDTO.convertToShipOwnerDTO
                (vesselsService.getVesselShipOwner(id), modelMapper));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<VesselDTO>> getVesselsByType(@PathVariable String type){
        return ResponseEntity.ok(vesselsService.allVesselsByType(type).stream()
                .map(vessel -> VesselDTO.convertToVesselDTO(vessel, modelMapper))
                .collect(Collectors.toList()));
    }

    @PutMapping("/refactor/{id}")
    public ResponseEntity<HttpStatus> refactorVesselInBase(@RequestBody VesselDTO vesselDTO,
                                                           @PathVariable Long id, BindingResult bindingResult,
                                                           StringBuilder stringBuilder) {
        notRefactoredException(bindingResult, vesselDTOValidator, stringBuilder, vesselDTO);
        vesselsService.refactorVesselInBase(id, VesselDTO.convertToVessel(vesselDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<HttpStatus> removeVesselFromBase(@PathVariable Long id) {
        vesselsService.removeVesselFromBase(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(AlreadyAddedToBaseException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(ListIsEmptyException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(NotFoundInBaseException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(NotCreatedException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(NotRefactoredException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
}