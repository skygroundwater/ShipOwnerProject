package ru.shipownerproject.utils.$dto.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shipownerproject.utils.$dto.ShipOwnerDTO;

@Component
public class ShipOwnerDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ShipOwnerDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ShipOwnerDTO shipOwnerDTO = (ShipOwnerDTO) target;
        if (shipOwnerDTO.getCountry() == null || shipOwnerDTO.getCountry().getName().isEmpty()) {
            errors.rejectValue("country", "", "Ship owner cannot to be unregistered for any country");
        }
        if (shipOwnerDTO.getName() == null || shipOwnerDTO.getName().isEmpty()) {
            errors.rejectValue("name", "",
                    "The name of shipowner is required to fill");
        }
        if (shipOwnerDTO.getDescription() == null || shipOwnerDTO.getDescription().isEmpty()) {
            errors.rejectValue("description", "",
                    "The description of shipowner is required to fill");
        }
    }
}