package ru.shipownerproject.services.vesselservice.typeservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.vesselrepository.vesseltyperepository.VesselTypeRepository;
import ru.shipownerproject.models.vessels.type.VesselType;

import java.util.Arrays;
import java.util.List;

@Service
public class VesselTypesServiceImpl implements VesselTypesService {

    private final VesselTypeRepository vesselTypeRepository;

    public static final String NVT = "This vessel type was not found, please select correctly";

    public VesselTypesServiceImpl(VesselTypeRepository vesselTypeRepository) {
        this.vesselTypeRepository = vesselTypeRepository;
    }

    @Override
    public String addAllVesselTypeToBase(){
        Arrays.stream(VesselType.Type.values()).forEach(
                type -> vesselTypeRepository.save(new VesselType(
                        type.getType(), type.getDescription())));
        return "All types have been added to base";
    }
    @Override
    public List<VesselType> allTypes(){
        return vesselTypeRepository.findAll();
    }
}
