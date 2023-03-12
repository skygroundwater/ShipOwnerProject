package ru.shipownerproject.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.services.countryservice.portservice.PortsService;
import ru.shipownerproject.utils.$dto.PortDTO;
import ru.shipownerproject.utils.$dto.validators.PortDTOValidator;
import ru.shipownerproject.utils.exceptions.*;

import static ru.shipownerproject.utils.exceptions.ErrorResponse.notCreatedException;
import static ru.shipownerproject.utils.exceptions.ErrorResponse.notRefactoredException;

@RestController
@RequestMapping("/ports")
public class PortsController {

    private final PortsService portsService;

    private final ModelMapper modelMapper;

    private final PortDTOValidator portDTOValidator;


    public PortsController(PortsService portsService, ModelMapper modelMapper, PortDTOValidator portDTOValidator) {
        this.portsService = portsService;
        this.modelMapper = modelMapper;
        this.portDTOValidator = portDTOValidator;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewCountryToDB(@RequestBody PortDTO portDTO, BindingResult bindingResult) {
        notCreatedException(bindingResult, portDTOValidator, new StringBuilder(), portDTO);
        portsService.addNewPort(PortDTO.convertToPort(portDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortDTO> getPortFromDB(@PathVariable Integer id) {
        return ResponseEntity.ok(PortDTO.convertToPortDTO(portsService.getPortFromDB(id), modelMapper));
    }

    @PutMapping("/refactor/{id}")
    public ResponseEntity<HttpStatus> refactorPort(@PathVariable Integer id, @RequestBody PortDTO portDTO,
                                                   BindingResult bindingResult) {
        notRefactoredException(bindingResult, portDTOValidator, new StringBuilder(), portDTO);
        portsService.refactorPort(id, PortDTO.convertToPort(portDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePortFromDB(@PathVariable Integer id) {
        portsService.deletePortFromDB(id);
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