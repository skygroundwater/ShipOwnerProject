package ru.shipownerproject.services.shipsownerservice;

public interface ShipsOwnerService {

    String shipOwner(String name);

    String shipOwnerVessels(String shipOwnerName);

    String addNewShipOwner(String country, String name,
                           String description);

    String setCountryForShipOwner(String shipOwnerName, String countryName);

    String setNameForShipOwner(String oldShipOwnerName, String newShipOwnerName);

    String removeFromBaseShipOwner(String shipOwnerName);
}
