package ru.shipownerproject.services.vesselservice;

import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;
import java.util.Optional;

public interface VesselsService {
    List<Country> allCountries();

    List<ShipOwner> allShipOwners();

    List<Vessel> allVessels();

    Vessel vessel(Long id);

    String vessel(String name);

    void addNewVessel(Vessel vessel);

    String addNewVessel(String vesselName, String country, String shipOwnerName, String IMO, Byte vesselType);

    String removeVesselFromBase(String IMO);

    List<Seaman> crew(Long id);

    String getInfoAboutCrew(String IMO);

    void removeVesselFromBase(Vessel vessel);

    void refactorVesselInBase(Vessel vessel, Long id);

    String refactorVesselInBase(String IMO, String newName,
                                String newCountry, Byte newVesselTypeId,
                                String newShipOwnerName);

    String allVesselsByType(Byte id);
}
