package ru.shipownerproject.utils.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shipownerproject.models.Port;
import ru.shipownerproject.utils.$dto.PortDTO;

@Component
public class PortDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Port.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PortDTO portDTO = (PortDTO) target;
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