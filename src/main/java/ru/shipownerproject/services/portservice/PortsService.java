package ru.shipownerproject.services.portservice;

import ru.shipownerproject.models.Port;
import ru.shipownerproject.models.Vessel;

import java.util.List;

public interface PortsService {

    Port findPortByName(String name);

    void addNewPort(Port port);

    void refactorPort(Port port);

    List<Vessel> vesselRegisteredThisPort(String name);

    void deletePortFromDB(String name);
}