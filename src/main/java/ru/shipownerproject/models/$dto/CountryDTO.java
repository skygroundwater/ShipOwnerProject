package ru.shipownerproject.models.$dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ru.shipownerproject.models.countries.Country;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {

    @NotEmpty(message = "Country cannot to be without name")
    private String name;

    public static Country convertToCountry(CountryDTO countryDTO, ModelMapper modelMapper){
        return modelMapper.map(countryDTO, Country.class);
    }

    public static CountryDTO convertToCountryDTO(Country country, ModelMapper modelMapper){
        return modelMapper.map(country, CountryDTO.class);
    }
}
