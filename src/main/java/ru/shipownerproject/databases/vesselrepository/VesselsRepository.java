package ru.shipownerproject.databases.vesselrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.models.vessels.type.VesselType;

import java.util.List;

public interface VesselsRepository extends JpaRepository<Vessel, Integer> {

    @Query("select v from Vessel v join fetch v.seamen join fetch v.port join fetch v.shipOwner join fetch v.country where v.IMO =:IMO")
    List<Vessel> findByIMO(Integer IMO);

    @Query("select v from Vessel v join fetch v.seamen join fetch v.port join fetch v.shipOwner join fetch v.country where v.vesselType =:type")
    List<Vessel> findVesselByVesselType(VesselType type);
}