package ru.shipownerproject.services.countryservice.portservice;

import ru.shipownerproject.models.countries.ports.Port;

public interface PortsService {


    Port findPortByName(String name);

    Port addNewPort(Port port);

    Port getPortFromDB(Integer id);

    Port refactorPort(Integer id, Port port);

    void deletePortFromDB(Integer id);
}
