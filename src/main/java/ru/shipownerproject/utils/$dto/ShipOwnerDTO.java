package ru.shipownerproject.utils.$dto;

import lombok.*;
import org.modelmapper.ModelMapper;
import ru.shipownerproject.models.shipowners.ShipOwner;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShipOwnerDTO extends DTO {

    private CountryDTO country;

    private String name;

    private String description;

    public static ShipOwner convertToShipowner(ShipOwnerDTO shipOwnerDTO, ModelMapper modelMapper) {
        return modelMapper.map(shipOwnerDTO, ShipOwner.class);
    }

    public static ShipOwnerDTO convertToShipOwnerDTO(ShipOwner shipOwner, ModelMapper modelMapper) {
        return new ShipOwnerDTO(CountryDTO.convertToCountryDTO(shipOwner.getCountry(), modelMapper),
                shipOwner.getName(), shipOwner.getDescription());
    }

    @Override
    public String toString() {
        return " Ship Owner";
    }
}