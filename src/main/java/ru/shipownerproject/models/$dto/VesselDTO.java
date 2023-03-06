package ru.shipownerproject.models.$dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class VesselDTO {

    @NotEmpty(message = "Vessel cannot to be unregistered in any country")
    private CountryDTO country;

    @NotEmpty(message = "Vessel cannot to be without ship owner")
    private ShipOwnerDTO shipOwner;

    @NotEmpty(message = "Vessel cannot to be without name")
    private String name;

    @NotEmpty(message = "IMO Number is required before building the vessel")
    private String IMO;

    @NotEmpty(message = "Vesel cannot to be non type")
    private String type;

    @NotNull(message = "Enter building date")
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
}