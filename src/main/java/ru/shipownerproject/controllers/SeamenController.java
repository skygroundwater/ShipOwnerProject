package ru.shipownerproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.services.seamanservice.SeamenService;
import ru.shipownerproject.utils.$dto.SeamanDTO;
import ru.shipownerproject.utils.validators.SeamanDTOValidator;

import static ru.shipownerproject.utils.exceptions.ErrorResponse.notCreatedException;
import static ru.shipownerproject.utils.exceptions.ErrorResponse.notRefactoredException;

@RestController
@RequestMapping("/seamen")
public class SeamenController {

    private final SeamenService seamenService;

    private final SeamanDTOValidator seamanDTOValidator;

    private final ModelMapper modelMapper;

    public SeamenController(SeamenService seamenService,
                            SeamanDTOValidator seamanDTOValidator, ModelMapper modelMapper) {
        this.seamenService = seamenService;
        this.seamanDTOValidator = seamanDTOValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewSeamanToBase(@RequestBody SeamanDTO seamanDTO,
                                                         BindingResult bindingResult) {
        notCreatedException(bindingResult, seamanDTOValidator, seamanDTO);
        seamenService.addNewSeamanToBase(SeamanDTO.convertToSeaman(seamanDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{passportNumber}")
    public ResponseEntity<Object> showInfoAboutSeaman(@PathVariable Integer passportNumber) {
        return ResponseEntity.ok(SeamanDTO.convertToSeamanDTO
                (seamenService.showInfoAboutSeaman(passportNumber), modelMapper));
    }

    @DeleteMapping("/{passportNumber}")
    public ResponseEntity<Object> removeSeamanFromBase(@PathVariable Integer passportNumber) {
        seamenService.removeSeamanFromBase(passportNumber);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/refactor")
    public ResponseEntity<HttpStatus> refactorSeamanInBase(@RequestBody SeamanDTO seamanDTO,
                                                           BindingResult bindingResult) {
        notRefactoredException(bindingResult, seamanDTOValidator, seamanDTO);
        seamenService.refactorSeamanInBase(SeamanDTO.convertToSeaman(seamanDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}