package ru.shipownerproject.services.shipsownerservice;

import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface ShipOwnersService {


    ShipOwner shipOwner(Long id);

    List<Vessel> shipOwnerVessels(Long id);

    void addNewShipOwner(ShipOwner shipOwner);

    void refactorShipOwner(Long id, ShipOwner shipOwner);

    void removeFromBaseShipOwner(Long id);
}
