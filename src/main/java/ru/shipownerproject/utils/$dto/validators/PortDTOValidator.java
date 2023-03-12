package ru.shipownerproject.utils.$dto.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shipownerproject.databases.countrybase.CountriesRepository;
import ru.shipownerproject.models.countries.ports.Port;
import ru.shipownerproject.services.countryservice.CountriesService;
import ru.shipownerproject.utils.$dto.PortDTO;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.NC;

@Component
public class PortDTOValidator implements Validator {

    private final CountriesService countriesService;

    public PortDTOValidator(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    private void checkCountryByName(PortDTO portDTO) {
        countriesService.findCountryByName(portDTO.getCountry().getName());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Port.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        PortDTO portDTO = (PortDTO) target;
        checkCountryByName(portDTO);

        if (portDTO.getCountry() == null || portDTO.getCountry().getName().isEmpty()) {
            errors.rejectValue("country", "", "Port cannot to be unregistered for any country");
        }
        if (portDTO.getName() == null || portDTO.getName().isEmpty()) {
            errors.rejectValue("name", "",
                    "The name of port is required to fill");
        }
        if (portDTO.getNav_description() == null || portDTO.getNav_description().isEmpty()) {
            errors.rejectValue("description", "",
                    "The description of port is required to fill");
        }
    }
}
