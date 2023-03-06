package ru.shipownerproject.databases.vesselrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

@Repository
public interface VesselRepository extends JpaRepository<Vessel, Long> {

    List<Vessel> findByIMO(Integer IMO);

}