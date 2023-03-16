package ru.shipownerproject.utils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import ru.shipownerproject.utils.$dto.DTO;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private String message;
    private long timestamp;

    public static void notCreatedException(BindingResult bindingResult, Validator validator, DTO dto) {
        validator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new NotCreatedException(
                    bindingResult.getFieldErrors().stream()
                            .map(FieldError::getDefaultMessage)
                            .collect(Collectors.joining("; ")));
        }
    }

    public static void notRefactoredException(BindingResult bindingResult, Validator validator, DTO dto) {
        validator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new NotRefactoredException(
                    bindingResult.getFieldErrors().stream()
                            .map(FieldError::getDefaultMessage)
                            .collect(Collectors.joining("; ")));
        }
    }

    public static List<?> whatIfEmpty(List<?> objects, String desc) {
        if (objects == null || objects.isEmpty()) throw new ListIsEmptyException(desc);
        else return objects;
    }
}