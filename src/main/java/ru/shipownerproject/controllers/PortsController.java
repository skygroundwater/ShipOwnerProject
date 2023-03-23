package ru.shipownerproject.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shipownerproject.services.portservice.PortsService;
import ru.shipownerproject.utils.$dto.PortDTO;
import ru.shipownerproject.utils.$dto.VesselDTO;
import ru.shipownerproject.utils.validators.PortDTOValidator;

import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("/vessels/{name}")
    public ResponseEntity<List<VesselDTO>> getRegisteredInPortVessels(@PathVariable String name){
        return ResponseEntity.ok(portsService.vesselRegisteredThisPort(name)
                .stream().map(vessel -> VesselDTO.convertToVesselDTO(vessel, modelMapper))
                .collect(Collectors.toList()));
    }

    @PutMapping("/refactor")
    public ResponseEntity<HttpStatus> refactorPort(@RequestBody PortDTO portDTO,
                                                   BindingResult bindingResult) {
        notRefactoredException(bindingResult, portDTOValidator, portDTO);
        portsService.refactorPort(PortDTO.convertToPort(portDTO, modelMapper));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<HttpStatus> deletePortFromDB(@PathVariable String name) {
        portsService.deletePortFromDB(name);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}