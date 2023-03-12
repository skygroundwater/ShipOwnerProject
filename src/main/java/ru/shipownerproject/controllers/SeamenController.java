package ru.shipownerproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.utils.$dto.SeamanDTO;
import ru.shipownerproject.utils.$dto.validators.SeamanDTOValidator;
import ru.shipownerproject.services.seamanservice.SeamenService;
import ru.shipownerproject.utils.exceptions.*;

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
    public ResponseEntity<HttpStatus> addNewSeamanToBase(@RequestBody SeamanDTO seamanDTO, BindingResult bindingResult,
                                                         StringBuilder stringBuilder) {
        notCreatedException(bindingResult, seamanDTOValidator, stringBuilder, seamanDTO);
        seamenService.addNewSeamanToBase(SeamanDTO.convertToSeaman(seamanDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> showInfoAboutSeaman(@PathVariable Long id) {
        return ResponseEntity.ok(SeamanDTO.convertToSeamanDTO
                (seamenService.showInfoAboutSeaman(id), modelMapper));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> removeSeamanFromBase(@PathVariable Long id){
        seamenService.removeSeamanFromBase(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/refactor/{id}")
    public ResponseEntity<HttpStatus> refactorSeamanInBase(@PathVariable Long id, @RequestBody SeamanDTO seamanDTO,
                                                           BindingResult bindingResult, StringBuilder stringBuilder) {
        notRefactoredException(bindingResult, seamanDTOValidator, stringBuilder, seamanDTO);
        seamenService.refactorSeamanInBase(id, SeamanDTO.convertToSeaman(seamanDTO, modelMapper));
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