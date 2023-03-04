package ru.shipownerproject.services.shipsownerservice;

import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface ShipOwnersService {

    List<Country> allCountries();

    List<ShipOwner> allShipOwners();

    ShipOwner shipOwner(Long id);

    List<Seaman> shipOwnerSeamen(Long id);

    List<Vessel> shipOwnerVessels(Long id);

    void addNewShipOwner(ShipOwner shipOwner);

    void refactorShipOwner(ShipOwner shipOwner, Long id);

    String setNameForShipOwner(String oldShipOwnerName, String newShipOwnerName);

    void removeFromBaseShipOwner(Long id);
}
