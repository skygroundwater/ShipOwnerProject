package ru.shipownerproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.exceptions.*;
import ru.shipownerproject.models.$dto.ShipOwnerDTO;
import ru.shipownerproject.models.$dto.VesselDTO;
import ru.shipownerproject.services.shipsownerservice.ShipOwnersService;

import java.util.stream.Collectors;

import static ru.shipownerproject.exceptions.ErrorResponse.notCreatedException;
import static ru.shipownerproject.exceptions.ErrorResponse.notRefactoredException;

@RestController
@RequestMapping("/shipowners")
public class ShipOwnersController {

    private final ShipOwnersService shipOwnersService;

    private final ModelMapper modelMapper;

    public ShipOwnersController(ShipOwnersService shipOwnersService, ModelMapper modelMapper) {
        this.shipOwnersService = shipOwnersService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewShipOwner(@RequestBody ShipOwnerDTO shipOwnerDTO, BindingResult bindingResult,
                                                      StringBuilder stringBuilder) {
        notCreatedException(bindingResult, stringBuilder, ". Ship Owner");
            shipOwnersService.addNewShipOwner(ShipOwnerDTO.convertToShipowner(shipOwnerDTO, modelMapper));
            return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> shipOwner(@PathVariable Long id) {
        return ResponseEntity.ok(ShipOwnerDTO.convertToShipOwnerDTO(shipOwnersService.shipOwner(id), modelMapper));
    }

    @GetMapping("/vessels/{id}")
    public ResponseEntity<Object> shipOwnerVessels(@PathVariable Long id) {
        return ResponseEntity.ok(shipOwnersService.shipOwnerVessels(id).stream()
                .map(vessel -> VesselDTO.convertToVesselDTO(vessel, modelMapper))
                .collect(Collectors.toList()));
    }

    @PutMapping("/refactor/{id}")
    public ResponseEntity<HttpStatus> refactorShipOwner(@PathVariable Long id, @RequestBody ShipOwnerDTO shipOwnerDTO,  BindingResult bindingResult,
                                                        StringBuilder stringBuilder) {
        notRefactoredException(bindingResult, stringBuilder, " Ship Owner");
        shipOwnersService.refactorShipOwner(id, ShipOwnerDTO.convertToShipowner(shipOwnerDTO, modelMapper));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteShipOwnerFromBase(@PathVariable Long id) {
        shipOwnersService.removeFromBaseShipOwner(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(AlreadyAddedToBaseException e) {
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