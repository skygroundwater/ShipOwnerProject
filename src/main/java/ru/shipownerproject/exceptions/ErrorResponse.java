package ru.shipownerproject.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private String message;
    private long timestamp;

    public static void notCreatedException(BindingResult bindingResult, StringBuilder stringBuilder, String object) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> stringBuilder.append(error.getField())
                    .append(" : ").append(error.getDefaultMessage()).append("\n"));
            throw new NotCreatedException(stringBuilder.append(object).toString());
        }
    }

    public static void notRefactoredException(BindingResult bindingResult, StringBuilder stringBuilder, String object) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> stringBuilder.append(error.getField())
                    .append(" : ").append(error.getDefaultMessage()).append("\n"));
            throw new NotRefactoredException(stringBuilder.append(object).toString());
        }
    }
}