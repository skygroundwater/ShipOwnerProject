package ru.shipownerproject.utils.$dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ru.shipownerproject.models.seaman.Seaman;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeamanDTO extends DTO {

    private CountryDTO citizenship;

    private VesselDTO vessel;

    private String fullName;

    private String position;

    private Date birth;

    private String birthPlace;

    private Integer passportNumber;

    public static Seaman convertToSeaman(SeamanDTO seamanDTO, ModelMapper modelMapper) {
        return modelMapper.map(seamanDTO, Seaman.class);
    }

    public static SeamanDTO convertToSeamanDTO(Seaman seaman, ModelMapper modelMapper) {
        return new SeamanDTO(CountryDTO.convertToCountryDTO(seaman.getCitizenship(), modelMapper),
                VesselDTO.convertToVesselDTO(seaman.getVessel(), modelMapper),
                seaman.getFullName(), seaman.getPosition(), seaman.getBirth(), seaman.getBirthPlace(),
                seaman.getPassportNumber());
    }

    @Override
    public String toString() {
        return " Seaman";
    }
}