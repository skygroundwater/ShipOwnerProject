package ru.shipownerproject.utils.$dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.models.vessels.type.VesselType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VesselDTO extends DTO{

    private CountryDTO country;

    private ShipOwnerDTO shipOwner;

    private String name;

    private Integer IMO;

    private String type;

    private LocalDate buildingDate;

    public static Vessel convertToVessel(VesselDTO vesselDTO, ModelMapper modelMapper){
        return new Vessel(vesselDTO.getName(), vesselDTO.getIMO(),
                ShipOwnerDTO.convertToShipowner(vesselDTO.getShipOwner(), modelMapper),
                VesselType.getVesselType(vesselDTO.getType()),
                CountryDTO.convertToCountry(vesselDTO.getCountry(), modelMapper), vesselDTO.getBuildingDate());
    }

    public static VesselDTO convertToVesselDTO(Vessel vessel, ModelMapper modelMapper){
        return new VesselDTO(CountryDTO.convertToCountryDTO(vessel.getCountry(), modelMapper),
                ShipOwnerDTO.convertToShipOwnerDTO(vessel.getShipOwner(), modelMapper),
                vessel.getName(), vessel.getIMO(), vessel.getVesselType().getType(), vessel.getDateOfBuild());
    }

    @Override
    public String toString(){
        return " Vessel";
    }
}