package ru.shipownerproject.utils.$dto.validators;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shipownerproject.utils.$dto.CountryDTO;

@Component
public class CountryDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CountryDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CountryDTO country = (CountryDTO) target;

        if (country.getName() == null || country.getName().isEmpty()) {
            errors.rejectValue("name", HttpStatus.BAD_REQUEST.toString(), "Country cannot to be without name");
        }
    }
}