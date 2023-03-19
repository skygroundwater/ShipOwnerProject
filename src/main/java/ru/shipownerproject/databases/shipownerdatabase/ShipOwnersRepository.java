package ru.shipownerproject.databases.shipownerdatabase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.shipownerproject.models.shipowners.ShipOwner;

import java.util.List;

public interface ShipOwnersRepository extends JpaRepository<ShipOwner, String> {

    List<ShipOwner> findShipOwnerByName(String name);

    @Query("select s from ShipOwner s join fetch s.vessels v join fetch v.port p where s.name =:name")
    List<ShipOwner> findByNameWithVessels(String name);

    @Query("select s from ShipOwner s join fetch s.seamen seaman join fetch seaman.vessel v join fetch v.port where s.name =:name")
    List<ShipOwner> findShipOwnerByNameWithSeamen(String name);


}
