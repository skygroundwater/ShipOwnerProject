package ru.shipownerproject.services.vesselservice;

import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface VesselsService {

    Vessel vessel(Long id);

    void addNewVessel(Vessel vessel, String IMO);

    void removeVesselFromBase(Long id);

    List<Seaman> getInfoAboutCrew(Long id);

    void refactorVesselInBase(Long id, Vessel vessel);

    List<Vessel> allVesselsByType(Short id);
}
