package ru.shipownerproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.exceptions.ErrorResponse;
import ru.shipownerproject.exceptions.NotFoundInBaseException;
import ru.shipownerproject.models.$dto.SeamanDTO;
import ru.shipownerproject.services.seamanservice.SeamenService;

@RestController
@RequestMapping("/seamen")
public class SeamenController {

    private final SeamenService seamenService;

    private final ModelMapper modelMapper;

    public SeamenController(SeamenService seamenService,
                            ModelMapper modelMapper) {
        this.seamenService = seamenService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewSeamanToBase(@RequestBody SeamanDTO seamanDTO) {
        seamenService.addNewSeamanToBase(SeamanDTO.convertToSeaman(seamanDTO, modelMapper));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> showInfoAboutSeaman(@PathVariable Long id) {
        return ResponseEntity.ok(SeamanDTO.convertToSeamanDTO(seamenService.showInfoAboutSeaman(id), modelMapper));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> removeSeamanFromBase(@PathVariable Long id){
        seamenService.removeSeamanFromBase(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/refactor/{id}")
    public ResponseEntity<HttpStatus> refactorSeamanInBase(@PathVariable Long id, @RequestBody SeamanDTO seamanDTO){
        seamenService.refactorSeamanInBase(id, SeamanDTO.convertToSeaman(seamanDTO, modelMapper));
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