package ru.shipownerproject.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shipownerproject.services.usersservice.DirectorService;
import ru.shipownerproject.services.usersservice.DirectorServiceImpl;
import ru.shipownerproject.utils.$dto.UserDTO;
import ru.shipownerproject.utils.$dto.validators.UserDTOValidator;
import ru.shipownerproject.utils.exceptions.*;

import java.io.IOException;

import static ru.shipownerproject.utils.exceptions.ErrorResponse.notCreatedException;

@RestController
@RequestMapping("/director")
public class ControllerForDirector {

    private final ModelMapper modelMapper;

    private final DirectorService directorService;

    private final UserDTOValidator userDTOValidator;

    public ControllerForDirector(ModelMapper modelMapper,
                                 DirectorService directorService,
                                 UserDTOValidator userDTOValidator) {
        this.modelMapper = modelMapper;
        this.directorService = directorService;
        this.userDTOValidator = userDTOValidator;
    }

    @PostMapping("/admin")
    public ResponseEntity<HttpStatus> addNewAdminToDB(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        notCreatedException(bindingResult, userDTOValidator, userDTO);
        directorService.registerNewAdmin(UserDTO.convertToUser(userDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/director")
    public ResponseEntity<HttpStatus> addNewDirector(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        notCreatedException(bindingResult, userDTOValidator, userDTO);
        directorService.registerNewDirector(UserDTO.convertToUser(userDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        notCreatedException(bindingResult, userDTOValidator, userDTO);
        directorService.registerNewUser(UserDTO.convertToUser(userDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String username) {
        directorService.deleteUser(username);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(IOException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
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
