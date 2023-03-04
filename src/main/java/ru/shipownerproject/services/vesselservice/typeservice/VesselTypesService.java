package ru.shipownerproject.services.vesselservice.typeservice;

import ru.shipownerproject.models.vessels.type.VesselType;

import java.util.List;

public interface VesselTypesService {
    String addAllVesselTypeToBase();

    List<VesselType> allTypes();
}
