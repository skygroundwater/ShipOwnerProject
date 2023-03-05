package ru.shipownerproject.models.$dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ru.shipownerproject.models.seaman.Seaman;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeamanDTO {

    @NotEmpty(message = "seaman cannot to be without citizenship")
    private CountryDTO citizenship;

    @NotEmpty(message = "Seaman cannot to be without vessel")
    private VesselDTO vessel;

    @NotEmpty(message = "Name for seaman should be entered")
    private String fullName;

    @NotEmpty(message = "Seaman cannot work on a vessel without position")
    private String position;

    @NotEmpty(message = "Enter birth date")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Birth date should to match pattern")
    private String birth;

    @NotEmpty(message = "Enter birth place")
    private String birthPlace;

    @NotEmpty(message = "Seaman cannot to be without passport")
    private String passport;

    public static Seaman convertToSeaman(SeamanDTO seamanDTO, ModelMapper modelMapper){
        return modelMapper.map(seamanDTO, Seaman.class);
    }
    public static SeamanDTO convertToSeamanDTO(Seaman seaman, ModelMapper modelMapper){
        return new SeamanDTO(CountryDTO.convertToCountryDTO(seaman.getCitizenship(), modelMapper),
                VesselDTO.convertToVesselDTO(seaman.getVessel(), modelMapper),
                seaman.getFullName(), seaman.getPosition(), seaman.getBirth(), seaman.getBirthPlace(),
                seaman.getSeamanPassport().getPassport());
    }
}