package ru.shipownerproject.services.vesselservice;

import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.countries.ports.Port;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface VesselsService {

    Vessel findVesselByIMO(Integer IMO);

    Vessel vessel(Long id);

    void addNewVessel(Vessel vessel);

    void removeVesselFromBase(Long id);

    List<Seaman> getInfoAboutCrew(Long id);

    Port getPortOfRegistration(Long id);

    ShipOwner getVesselShipOwner(Long id);

    Country getCountryOfRegistration(Long id);

    void refactorVesselInBase(Long id, Vessel vessel);

    List<Vessel> allVesselsByType(String type);
}
