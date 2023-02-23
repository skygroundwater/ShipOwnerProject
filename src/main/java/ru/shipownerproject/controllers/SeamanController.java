package ru.shipownerproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shipownerproject.services.seamanservice.SeamanService;

@RestController
@RequestMapping("/seaman")
public class SeamanController {

    private final SeamanService seamanService;

    public SeamanController(SeamanService seamanService) {
        this.seamanService = seamanService;
    }

    @GetMapping("/get")
    public ResponseEntity<String> showInfoAboutSeaman(String fullName) {
        return ResponseEntity.ok(seamanService.showInfoAboutSeaman(fullName));
    }

    @PostMapping
    public ResponseEntity<String> addNewSeamanToBase(String fullName, String position, String birthDate,
                                                     String birthPlace, String citizenship, String IMO) {
        return ResponseEntity.ok(seamanService.
                addNewSeamanToBase(fullName, position, birthDate, birthPlace, citizenship, IMO));
    }
}