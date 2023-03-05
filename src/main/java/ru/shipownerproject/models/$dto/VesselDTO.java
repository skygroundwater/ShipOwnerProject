package ru.shipownerproject.models.$dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ru.shipownerproject.models.vessels.Vessel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VesselDTO {

    @NotEmpty(message = "Vessel cannot to be unregistered in any country")
    private CountryDTO country;

    @NotEmpty(message = "Vessel cannot to be without ship owner")
    private ShipOwnerDTO shipOwner;

    @NotEmpty(message = "Vessel cannot to be without name")
    private String name;

    @NotEmpty(message = "IMO Number is required before building the vessel")
    private String IMO;

    @NotEmpty(message = "Vesel cannot to bew non type")
    private VesselTypeDTO vesselType;

    public static Vessel convertToVessel(VesselDTO vesselDTO, ModelMapper modelMapper){
        return new Vessel(vesselDTO.getName(), vesselDTO.getIMO(),
                ShipOwnerDTO.convertToShipowner(vesselDTO.getShipOwner(), modelMapper),
                VesselTypeDTO.convertToVesselType(vesselDTO.getVesselType(), modelMapper),
                CountryDTO.convertToCountry(vesselDTO.getCountry(), modelMapper));
    }

    public static VesselDTO convertToVesselDTO(Vessel vessel, ModelMapper modelMapper){
        return new VesselDTO(CountryDTO.convertToCountryDTO(vessel.getCountry(), modelMapper),
                ShipOwnerDTO.convertToShipOwnerDTO(vessel.getShipOwner(), modelMapper),
                vessel.getName(), vessel.getIMO(), VesselTypeDTO.convertToVesselTypeDTO(vessel.getVesselType(), modelMapper));
    }

}
