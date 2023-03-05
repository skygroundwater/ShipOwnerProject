package ru.shipownerproject.databases.vesselrepository.vesseltyperepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.models.vessels.type.VesselType;

public interface VesselTypeRepository extends JpaRepository<VesselType, Short> {

    VesselType findByType(String type);

}
