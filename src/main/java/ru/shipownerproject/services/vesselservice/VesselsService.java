package ru.shipownerproject.services.vesselservice;

import ru.shipownerproject.models.Country;
import ru.shipownerproject.models.Port;
import ru.shipownerproject.models.Seaman;
import ru.shipownerproject.models.ShipOwner;
import ru.shipownerproject.models.Vessel;

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
