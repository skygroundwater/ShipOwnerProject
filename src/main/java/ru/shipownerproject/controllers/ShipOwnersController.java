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
    public ResponseEntity<HttpStatus> addNewShipOwner(@RequestBody ShipOwnerDTO shipOwnerDTO,
                                                      BindingResult bindingResult) {
        notCreatedException(bindingResult, shipOwnerDTOValidator,  shipOwnerDTO);
        shipOwnersService.addNewShipOwner(ShipOwnerDTO.convertToShipowner(shipOwnerDTO, modelMapper));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<ShipOwnerDTO> shipOwner(@PathVariable String name) {
        return ResponseEntity.ok(
                ShipOwnerDTO.convertToShipOwnerDTO(
                        shipOwnersService.findShipOwnerByName(name), modelMapper));
    }

    @GetMapping("/vessels/{name}")
    public ResponseEntity<List<VesselDTO>> shipOwnerVessels(@PathVariable String name) {
        return ResponseEntity.ok(shipOwnersService.shipOwnerVessels(name).stream()
                .map(vessel -> VesselDTO.convertToVesselDTO(vessel, modelMapper))
                .collect(Collectors.toList()));
    }

    @GetMapping("/seamen/{name}")
    public ResponseEntity<List<SeamanDTO>> shipOwnerSeamen(@PathVariable String name){
        return ResponseEntity.ok(shipOwnersService.shipOwnerSeamen(name).stream()
                .map(seaman -> SeamanDTO.convertToSeamanDTO(seaman, modelMapper))
                .collect(Collectors.toList()));
    }

    @PutMapping("/refactor/{name}")
    public ResponseEntity<HttpStatus> refactorShipOwner(@PathVariable String name,
                                                        @RequestBody ShipOwnerDTO shipOwnerDTO,
                                                        BindingResult bindingResult) {
        notRefactoredException(bindingResult, shipOwnerDTOValidator, shipOwnerDTO);
        shipOwnersService.refactorShipOwner(name, ShipOwnerDTO.convertToShipowner(shipOwnerDTO, modelMapper));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<HttpStatus> deleteShipOwnerFromBase(@PathVariable String name) {
        shipOwnersService.removeFromBaseShipOwner(name);
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