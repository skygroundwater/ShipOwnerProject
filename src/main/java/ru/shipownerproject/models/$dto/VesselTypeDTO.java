package ru.shipownerproject.models.$dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ru.shipownerproject.models.vessels.type.VesselType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VesselTypeDTO {

    @NotEmpty
    private String type;

    public static VesselType convertToVesselType(VesselTypeDTO vesselTypeDTO, ModelMapper modelMapper){
        return new VesselType(vesselTypeDTO.getType());
    }
    public static VesselTypeDTO convertToVesselTypeDTO(VesselType vesselType, ModelMapper modelMapper){
        return new VesselTypeDTO(vesselType.getType());
    }

}
