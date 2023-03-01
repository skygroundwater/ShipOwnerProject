package ru.shipownerproject.services.shipsownerservice;

import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface ShipOwnersService {

    String shipOwner(String name);

    String shipOwnerVessels(String shipOwnerName);

    String addNewShipOwner(String country, String name,
                           String description);

    String refactorCountryForShipOwner(String shipOwnerName, String countryName);

    String setNameForShipOwner(String oldShipOwnerName, String newShipOwnerName);

    String removeFromBaseShipOwner(String shipOwnerName);
}
