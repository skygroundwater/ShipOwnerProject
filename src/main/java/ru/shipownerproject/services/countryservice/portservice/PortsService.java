package ru.shipownerproject.services.countryservice.portservice;

import ru.shipownerproject.models.countries.ports.Port;

public interface PortsService {


    Port findPortByName(String name);

    Port addNewPort(Port port);

    Port refactorPort(Port port);

    void deletePortFromDB(String name);
}
