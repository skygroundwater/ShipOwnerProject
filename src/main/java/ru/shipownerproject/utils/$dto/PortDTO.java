package ru.shipownerproject.utils.$dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ru.shipownerproject.models.countries.ports.Port;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PortDTO extends DTO {

    private CountryDTO country;

    private String name;

    private String nav_description;

    public static Port convertToPort(PortDTO portDTO, ModelMapper modelMapper) {
        return new Port(portDTO.getName(), CountryDTO.convertToCountry(portDTO.getCountry(), modelMapper), portDTO.getNav_description());
    }

    public static PortDTO convertToPortDTO(Port port, ModelMapper modelMapper) {
        return new PortDTO(CountryDTO.convertToCountryDTO(port.getCountry(), modelMapper),
                port.getName(), port.getNav_description());
    }

    @Override
    public String toString() {
        return " Port";
    }
}