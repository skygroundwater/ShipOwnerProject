package ru.shipownerproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.services.shipsownerservice.ShipOwnersService;
import ru.shipownerproject.utils.$dto.SeamanDTO;
import ru.shipownerproject.utils.$dto.ShipOwnerDTO;
import ru.shipownerproject.utils.$dto.VesselDTO;
import ru.shipownerproject.utils.$dto.validators.ShipOwnerDTOValidator;
import ru.shipownerproject.utils.exceptions.*;

import java.util.List;
import java.util.stream.Collectors;

import static ru.shipownerproject.utils.exceptions.ErrorResponse.notCreatedException;
import static ru.shipownerproject.utils.exceptions.ErrorResponse.notRefactoredException;

@RestController
@RequestMapping("/shipowners")
public class ShipOwnersController {

    private final ShipOwnersService shipOwnersService;

    private final ShipOwnerDTOValidator shipOwnerDTOValidator;

    private final ModelMapper modelMapper;

    public ShipOwnersController(ShipOwnersService shipOwnersService, ShipOwnerDTOValidator shipOwnerDTOValidator, ModelMapper modelMapper) {
        this.shipOwnersService = shipOwnersService;
        this.shipOwnerDTOValidator = shipOwnerDTOValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewShipOwner(@RequestBody ShipOwnerDTO shipOwnerDTO, BindingResult bindingResult,
                                                      StringBuilder stringBuilder) {
        notCreatedException(bindingResult, shipOwnerDTOValidator, stringBuilder, shipOwnerDTO);
        shipOwnersService.addNewShipOwner(ShipOwnerDTO.convertToShipowner(shipOwnerDTO, modelMapper));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipOwnerDTO> shipOwner(@PathVariable Long id) {
        return ResponseEntity.ok(ShipOwnerDTO.convertToShipOwnerDTO(shipOwnersService.shipOwner(id), modelMapper));
    }

    @GetMapping("/vessels/{id}")
    public ResponseEntity<List<VesselDTO>> shipOwnerVessels(@PathVariable Long id) {
        return ResponseEntity.ok(shipOwnersService.shipOwnerVessels(id).stream()
                .map(vessel -> VesselDTO.convertToVesselDTO(vessel, modelMapper))
                .collect(Collectors.toList()));
    }

    @GetMapping("/seamen/{id}")
    public ResponseEntity<List<SeamanDTO>> shipOwnerSeamen(@PathVariable Long id){
        return ResponseEntity.ok(shipOwnersService.shipOwnerSeamen(id).stream()
                .map(seaman -> SeamanDTO.convertToSeamanDTO(seaman, modelMapper))
                .collect(Collectors.toList()));
    }

    @PutMapping("/refactor/{id}")
    public ResponseEntity<HttpStatus> refactorShipOwner(@PathVariable Long id, @RequestBody ShipOwnerDTO shipOwnerDTO, BindingResult bindingResult,
                                                        StringBuilder stringBuilder) {
        notRefactoredException(bindingResult, shipOwnerDTOValidator, stringBuilder, shipOwnerDTO);
        shipOwnersService.refactorShipOwner(id, ShipOwnerDTO.convertToShipowner(shipOwnerDTO, modelMapper));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteShipOwnerFromBase(@PathVariable Long id) {
        shipOwnersService.removeFromBaseShipOwner(id);
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