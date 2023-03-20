package ru.shipownerproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.services.countryservice.CountriesService;
import ru.shipownerproject.utils.$dto.*;
import ru.shipownerproject.utils.$dto.validators.CountryDTOValidator;
import ru.shipownerproject.utils.exceptions.*;

import java.util.List;
import java.util.stream.Collectors;

import static ru.shipownerproject.utils.exceptions.ErrorResponse.notCreatedException;

@RestController
@RequestMapping("/countries")
public class CountriesController {

    private final CountriesService countriesService;

    private final CountryDTOValidator countryDTOValidator;

    private final ModelMapper modelMapper;

    public CountriesController(CountriesService countriesService, ModelMapper modelMapper,
                               CountryDTOValidator countryDTOValidator) {
        this.countriesService = countriesService;
        this.modelMapper = modelMapper;
        this.countryDTOValidator = countryDTOValidator;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewCountry(@RequestBody CountryDTO countryDTO, BindingResult bindingResult) {
        notCreatedException(bindingResult, countryDTOValidator, countryDTO);
        countriesService.newCountry(CountryDTO.convertToCountry(countryDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> allCountries() {
        return ResponseEntity.ok(countriesService.allCountries().stream()
                .map(country -> CountryDTO.convertToCountryDTO(country, modelMapper))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> returnCountry(@PathVariable String name) {
        return ResponseEntity.ok(CountryDTO.convertToCountryDTO(
                countriesService.findCountryByName(name), modelMapper));
    }

    @GetMapping("/shipowners/{name}")
    public ResponseEntity<List<ShipOwnerDTO>> returnShipOwnersRegisteredInCountry(@PathVariable String name) {
        return ResponseEntity.ok(countriesService.returnShipOwnersRegisteredInCountry(name).stream()
                .map(shipOwner -> ShipOwnerDTO.convertToShipOwnerDTO(shipOwner, modelMapper))
                .collect(Collectors.toList()));
    }

    @GetMapping("/vessels/{name}")
    public ResponseEntity<List<VesselDTO>> returnVesselsRegisteredInCountry(@PathVariable String name){
        return ResponseEntity.ok(countriesService.returnVesselsRegisteredInCountry(name).stream()
                .map(vessel -> VesselDTO.convertToVesselDTO(vessel, modelMapper))
                .collect(Collectors.toList()));
    }

    @GetMapping("/seamen/{name}")
    public ResponseEntity<List<SeamanDTO>> returnSeamenWithCitizenshipOfCountry(@PathVariable String name){
        return ResponseEntity.ok(countriesService.seamenWithCitizenShipOfCountry(name).stream()
                .map(seaman -> SeamanDTO.convertToSeamanDTO(seaman, modelMapper))
                .collect(Collectors.toList()));
    }
    @GetMapping("/ports/{name}")
    public ResponseEntity<List<PortDTO>> returnPortsOfThisCountry(@PathVariable String name){
        return ResponseEntity.ok(countriesService.portsInCountry(name).stream()
                .map(port -> PortDTO.convertToPortDTO(port, modelMapper))
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<HttpStatus> deleteCountry(@PathVariable String name) {
        countriesService.deleteCountry(name);
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
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
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