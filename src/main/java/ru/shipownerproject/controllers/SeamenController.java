package ru.shipownerproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.services.seamanservice.SeamenService;

@RestController
@RequestMapping("/seaman")
public class SeamenController {

    private final SeamenService seamenService;

    public SeamenController(SeamenService seamenService) {
        this.seamenService = seamenService;
    }

    @GetMapping("/get")
    public ResponseEntity<String> showInfoAboutSeaman(String passport) {
        return ResponseEntity.ok(seamenService.showInfoAboutSeaman(passport));
    }

    @PostMapping
    public ResponseEntity<String> addNewSeamanToBase(String fullName, String seamanPassport, String position, String birthDate,
                                                     String birthPlace, String citizenship, String IMO) {
        return ResponseEntity.ok(seamenService.
                addNewSeamanToBase(fullName, seamanPassport, position, birthDate, birthPlace, citizenship, IMO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeSeamanFromBase(String passport){
        return ResponseEntity.ok(seamenService.removeSeamanFromBaseByPassport(passport));
    }

    @PutMapping("/refactor")
    public ResponseEntity<String> refactorSeamanInBase(String passport,String newFullName, String newPosition, String newBirthDate,
                                                       String newBirthPlace, String newCitizenship){
        return ResponseEntity.ok(seamenService.refactorSeamanInBase(passport, newFullName, newPosition, newBirthDate, newBirthPlace, newCitizenship));
    }

}