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
    public ResponseEntity<HttpStatus> addNewPort(@RequestBody PortDTO portDTO, BindingResult bindingResult) {
        notCreatedException(bindingResult, portDTOValidator, portDTO);
        portsService.addNewPort(PortDTO.convertToPort(portDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<PortDTO> getPortFromDB(@PathVariable String name) {
        return ResponseEntity.ok(PortDTO.convertToPortDTO(portsService.findPortByName(name), modelMapper));
    }

    @PutMapping("/refactor")
    public ResponseEntity<HttpStatus> refactorPort(@RequestBody PortDTO portDTO,
                                                   BindingResult bindingResult) {
        notRefactoredException(bindingResult, portDTOValidator, portDTO);
        portsService.refactorPort(PortDTO.convertToPort(portDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<HttpStatus> deletePortFromDB(@PathVariable String name) {
        portsService.deletePortFromDB(name);
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