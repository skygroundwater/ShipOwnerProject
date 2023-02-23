package ru.shipownerproject.databases.shipownerdatabase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shipownerproject.models.shipowners.ShipOwner;

import java.util.List;

@Repository
public interface ShipOwnerRepository extends JpaRepository<ShipOwner, Long> {
    List<ShipOwner> findByName(String name);

    void deleteByName(String name);
}
