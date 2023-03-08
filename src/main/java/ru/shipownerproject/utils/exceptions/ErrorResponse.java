package ru.shipownerproject.utils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ru.shipownerproject.utils.$dto.DTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private String message;
    private long timestamp;

    public static void notCreatedException(BindingResult bindingResult, Validator validator,
                                           StringBuilder stringBuilder, DTO dto) {
        validator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> stringBuilder.append(error.getField())
                    .append(" : ").append(error.getDefaultMessage()).append(";  "));
            throw new NotCreatedException(stringBuilder.append(".")
                    .append("Intermediate object for ").append(dto).toString());
        }
    }

    public static void notRefactoredException(BindingResult bindingResult, Validator validator,
                                              StringBuilder stringBuilder, DTO dto) {
        validator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> stringBuilder.append(error.getField())
                    .append(" : ").append(error.getDefaultMessage()).append(";  "));
            throw new NotRefactoredException(stringBuilder.append(".")
                    .append("Intermediate object for ").append(dto).toString());
        }
    }
}