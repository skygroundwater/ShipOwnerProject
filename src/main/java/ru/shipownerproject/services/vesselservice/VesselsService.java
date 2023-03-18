package ru.shipownerproject.services.vesselservice;

import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.countries.ports.Port;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface VesselsService {

    Vessel findVesselByIMO(Integer IMO);

    void addNewVessel(Vessel vessel);

    void removeVesselFromBase(Integer IMO);

    List<Seaman> getInfoAboutCrew(Integer IMO);

    Port getPortOfRegistration(Integer IMO);

    ShipOwner getVesselShipOwner(Integer IMO);

    Country getCountryOfRegistration(Integer IMO);

    void refactorVesselInBase(Vessel vessel);

    List<Vessel> allVesselsByType(String type);
}
