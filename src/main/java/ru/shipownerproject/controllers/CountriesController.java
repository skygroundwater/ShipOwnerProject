package ru.shipownerproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.utils.$dto.CountryDTO;
import ru.shipownerproject.utils.$dto.ShipOwnerDTO;
import ru.shipownerproject.utils.$dto.VesselDTO;
import ru.shipownerproject.utils.$dto.validators.CountryDTOValidator;
import ru.shipownerproject.services.countryservice.CountriesService;
import ru.shipownerproject.utils.exceptions.*;

import java.util.stream.Collectors;

import static ru.shipownerproject.utils.exceptions.ErrorResponse.notCreatedException;
import static ru.shipownerproject.utils.exceptions.ErrorResponse.notRefactoredException;

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

        notCreatedException(bindingResult, countryDTOValidator, new StringBuilder(), countryDTO);
        countriesService.newCountry(CountryDTO.convertToCountry(countryDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> allCountries(){
        return ResponseEntity.ok(countriesService.allCountries().stream()
                .map(country -> CountryDTO.convertToCountryDTO(country, modelMapper))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> returnCountry(@PathVariable Integer id) {
        return ResponseEntity.ok(CountryDTO.convertToCountryDTO(
                countriesService.oneCountry(id), modelMapper));
    }

    @GetMapping("/shipowners/{id}")
    public ResponseEntity<Object> returnCountryShipOwners(@PathVariable Integer id) {
        return ResponseEntity.ok(countriesService.countryShipOwners(id).stream()
                .map(shipOwner -> ShipOwnerDTO.convertToShipOwnerDTO(shipOwner, modelMapper))
                .collect(Collectors.toList()));
    }

    @GetMapping("/vessels/{id}")
    public ResponseEntity<Object> returnCountryVessels(@PathVariable Integer id){
        return ResponseEntity.ok(countriesService.countryVessels(id).stream()
                .map(vessel -> VesselDTO.convertToVesselDTO(vessel, modelMapper))
                .collect(Collectors.toList()));
    }

    @PutMapping("/refactor/{id}")
    public ResponseEntity<HttpStatus> refactorCountryName(@PathVariable Integer id,
                                                          @RequestBody CountryDTO countryDTO,  BindingResult bindingResult,
                                                          StringBuilder stringBuilder) {
        notRefactoredException(bindingResult, countryDTOValidator, stringBuilder, countryDTO);
        countriesService.refactorCountryName(id, CountryDTO.convertToCountry(countryDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(AlreadyAddedToBaseException e) {
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