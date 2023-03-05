package ru.shipownerproject.models.$dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.modelmapper.ModelMapper;
import ru.shipownerproject.models.shipowners.ShipOwner;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipOwnerDTO {

    @NotEmpty(message = "Ship owner cannot to be unregistered for any country")
    private CountryDTO country;

    @NotEmpty(message = "The name of shipowner is required to fill")
    private String name;

    @NotEmpty(message = "The description of shipowner is required to fill")
    private String description;

    public ShipOwnerDTO(String name){
        this.name = name;
    }

    public static ShipOwner convertToShipowner(ShipOwnerDTO shipOwnerDTO, ModelMapper modelMapper){
        return modelMapper.map(shipOwnerDTO, ShipOwner.class);
    }

    public static ShipOwnerDTO convertToShipOwnerDTO(ShipOwner shipOwner, ModelMapper modelMapper){
        return new ShipOwnerDTO(CountryDTO.convertToCountryDTO(shipOwner.getCountry(), modelMapper),
                shipOwner.getName(), shipOwner.getDescription());
    }
}