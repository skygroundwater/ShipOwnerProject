package ru.shipownerproject.utils.$dto.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shipownerproject.utils.$dto.UserDTO;

@Component
public class UserDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserDTO userDTO = (UserDTO) target;
        if (userDTO.getUsername().isEmpty() || userDTO.getUsername() == null)
            errors.rejectValue("username", "Username cannot to be null or Empty");

        if (userDTO.getPassword().isEmpty() || userDTO.getPassword() == null)
            errors.rejectValue("password", "Password for user cannot to be empty or null");

    }
}
