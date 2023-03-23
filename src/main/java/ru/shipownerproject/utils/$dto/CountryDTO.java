package ru.shipownerproject.utils.$dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ru.shipownerproject.models.Country;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO extends DTO {

    private String name;

    public static Country convertToCountry(CountryDTO countryDTO, ModelMapper modelMapper) {
        return modelMapper.map(countryDTO, Country.class);
    }

    public static CountryDTO convertToCountryDTO(Country country, ModelMapper modelMapper) {
        return modelMapper.map(country, CountryDTO.class);
    }

    @Override
    public String toString() {
        return "Country ";
    }
}