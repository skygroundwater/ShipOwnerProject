package ru.shipownerproject.utils.$dto.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shipownerproject.databases.countrybase.CountriesRepository;
import ru.shipownerproject.databases.vesselrepository.VesselsRepository;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;
import ru.shipownerproject.utils.$dto.SeamanDTO;

import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.NC;
import static ru.shipownerproject.services.vesselservice.VesselsServiceImpl.NV;

@Component
public class SeamanDTOValidator implements Validator {

    private final VesselsRepository vesselsRepository;

    private final CountriesRepository countriesRepository;

    public SeamanDTOValidator(VesselsRepository vesselsRepository,
                              CountriesRepository countriesRepository) {
        this.vesselsRepository = vesselsRepository;
        this.countriesRepository = countriesRepository;
    }

    private void checkVesselByIMO(Integer IMO) {
        vesselsRepository.findByIMO(IMO).stream().findAny().orElseThrow(()
                -> new NotFoundInBaseException(NV));
    }

    private void checkCountryByName(String countryName) {
        countriesRepository.findByName(countryName).stream().findAny().orElseThrow(()
                -> new NotFoundInBaseException(NC));
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SeamanDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SeamanDTO seaman = (SeamanDTO) target;
        checkCountryByName(seaman.getCitizenship().getName());
        checkVesselByIMO(seaman.getVessel().getIMO());
        if (seaman.getCitizenship() == null || seaman.getCitizenship().getName().isEmpty()) {
            errors.rejectValue("country", "",
                    "Seaman cannot to be without citizenship");
        }
        if (seaman.getFullName() == null || seaman.getFullName().isEmpty()) {
            errors.rejectValue("fullName", "",
                    "Name for seaman should be entered");
        }
        if (seaman.getVessel() == null || seaman.getVessel().getIMO() == null ||
                String.valueOf(seaman.getVessel().getIMO()).length() != 7) {
            errors.rejectValue("vessel", "", NV);
        }
        if (seaman.getPosition() == null || seaman.getPosition().isEmpty()) {
            errors.rejectValue("position", "",
                    "Seaman cannot to be boarded hasn't position");
        }
        if (seaman.getBirth() == null) {
            errors.rejectValue("birth", "", "Enter birth date");
        }
        if (seaman.getBirthPlace() == null || seaman.getBirthPlace().isEmpty()) {
            errors.rejectValue("birthPlace", "", "Enter birth place");
        }
        if (seaman.getPassport() == null || seaman.getPassport().isEmpty()) {
            errors.rejectValue("passport", "",
                    "Seaman cannot to be without passport");
        }
    }
}
