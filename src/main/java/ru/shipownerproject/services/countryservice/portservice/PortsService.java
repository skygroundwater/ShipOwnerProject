package ru.shipownerproject.services.countryservice.portservice;

import ru.shipownerproject.models.countries.ports.Port;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface PortsService {

    Port findPortByName(String name);

    void addNewPort(Port port);

    void refactorPort(Port port);

    List<Vessel> vesselRegisteredThisPort(String name);

    void deletePortFromDB(String name);
}