package ru.shipownerproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.shipownerproject.models.Vessel;
import ru.shipownerproject.models.enums.VesselType;

import java.util.List;

public interface VesselsRepository extends JpaRepository<Vessel, Integer> {

    @Query("select v from Vessel v join fetch v.port join fetch v.shipOwner join fetch v.country where v.IMO =:IMO")
    List<Vessel> findByIMO(Integer IMO);

    @Query("select v from Vessel v join fetch v.seamen join fetch v.port join fetch v.shipOwner join fetch v.country where v.IMO =:IMO")
    List<Vessel> findByIMOWithSeamen(Integer IMO);

    @Query("select v from Vessel v join fetch v.country c join fetch v.shipOwner s join fetch v.port p where v.vesselType =:type")
    List<Vessel> findVesselByVesselType(VesselType type);
}