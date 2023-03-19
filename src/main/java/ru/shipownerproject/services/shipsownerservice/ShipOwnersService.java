package ru.shipownerproject.services.shipsownerservice;

import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface ShipOwnersService {

    ShipOwner findShipOwnerByName(String name);

    ShipOwner findShipOwnerByNameWithVessels(String name);

    ShipOwner findShipOwnerByNameWithSeamen(String name);

    List<Vessel> shipOwnerVessels(String name);

    List<Seaman> shipOwnerSeamen(String name);

    void addNewShipOwner(ShipOwner shipOwner);

    void refactorShipOwner(ShipOwner shipOwner);

    void removeFromBaseShipOwner(String name);
}
