package ru.shipownerproject.utils.$dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ru.shipownerproject.models.Vessel;
import ru.shipownerproject.models.enums.VesselType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VesselDTO extends DTO {

    private CountryDTO country;

    private ShipOwnerDTO shipOwner;

    private String name;

    private Integer IMO;

    private String type;

    private PortDTO port;

    private LocalDate buildingDate;

    public VesselDTO(Integer IMO) {
        this.IMO = IMO;
    }

    public static Vessel convertToVessel(VesselDTO vesselDTO, ModelMapper modelMapper) {
        return new Vessel(vesselDTO.getName(), vesselDTO.getIMO(),
                ShipOwnerDTO.convertToShipowner(vesselDTO.getShipOwner(), modelMapper),
                VesselType.getVesselType(vesselDTO.getType()),
                CountryDTO.convertToCountry(vesselDTO.getCountry(), modelMapper),
                PortDTO.convertToPort(vesselDTO.getPort(), modelMapper),
                vesselDTO.getBuildingDate());
    }

    public static VesselDTO convertToVesselDTO(Vessel vessel, ModelMapper modelMapper) {
        return new VesselDTO(CountryDTO.convertToCountryDTO(vessel.getCountry(), modelMapper),
                ShipOwnerDTO.convertToShipOwnerDTO(vessel.getShipOwner(), modelMapper),
                vessel.getName(), vessel.getIMO(), vessel.getVesselType().getType(),
                PortDTO.convertToPortDTO(vessel.getPort(), modelMapper), vessel.getDateOfBuild());
    }

    @Override
    public String toString() {
        return " Vessel";
    }
}