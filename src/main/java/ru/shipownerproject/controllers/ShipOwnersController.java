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
import ru.shipownerproject.utils.validators.ShipOwnerDTOValidator;

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

    public ShipOwnersController(ShipOwnersService shipOwnersService,
                                ShipOwnerDTOValidator shipOwnerDTOValidator,
                                ModelMapper modelMapper) {
        this.shipOwnersService = shipOwnersService;
        this.shipOwnerDTOValidator = shipOwnerDTOValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewShipOwner(@RequestBody ShipOwnerDTO shipOwnerDTO,
                                                      BindingResult bindingResult) {
        notCreatedException(bindingResult, shipOwnerDTOValidator, shipOwnerDTO);
        shipOwnersService.addNewShipOwner(ShipOwnerDTO.convertToShipowner(shipOwnerDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
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
    public ResponseEntity<List<SeamanDTO>> shipOwnerSeamen(@PathVariable String name) {
        return ResponseEntity.ok(shipOwnersService.shipOwnerSeamen(name).stream()
                .map(seaman -> SeamanDTO.convertToSeamanDTO(seaman, modelMapper))
                .collect(Collectors.toList()));
    }

    @PutMapping("/refactor")
    public ResponseEntity<HttpStatus> refactorShipOwner(@RequestBody ShipOwnerDTO shipOwnerDTO,
                                                        BindingResult bindingResult) {
        notRefactoredException(bindingResult, shipOwnerDTOValidator, shipOwnerDTO);
        shipOwnersService.refactorShipOwner(ShipOwnerDTO.convertToShipowner(shipOwnerDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<HttpStatus> deleteShipOwnerFromBase(@PathVariable String name) {
        shipOwnersService.removeFromBaseShipOwner(name);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}