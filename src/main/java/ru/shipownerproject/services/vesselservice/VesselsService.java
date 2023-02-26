package ru.shipownerproject.services.vesselservice;

public interface VesselsService {
    String vessel(String name);

    String addNewVessel(String vesselName, String country, String shipOwnerName, String IMO, Short vesselType);

    String removeVesselFromBase(String IMO);

    String getInfoAboutCrew(String IMO);

    String refactorVesselInBase(String IMO, String newName,
                                String newCountry, Short newVesselTypeId,
                                String newShipOwnerName);

    String allVesselsByType(Short id);
}
