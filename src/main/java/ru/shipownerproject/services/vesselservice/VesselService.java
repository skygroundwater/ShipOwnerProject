package ru.shipownerproject.services.vesselservice;

public interface VesselService {
    String vessel(String name);

    String addNewVessel(String vesselName, String shipOwnerName,
                        String IMO);

    String removeVesselFromBase(String IMO);
}
