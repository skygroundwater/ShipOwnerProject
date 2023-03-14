package ru.shipownerproject.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class GeneralController {

    @GetMapping
    public String hello(){
        return "Hello, This is project for communication between shipowners";
    }

}
