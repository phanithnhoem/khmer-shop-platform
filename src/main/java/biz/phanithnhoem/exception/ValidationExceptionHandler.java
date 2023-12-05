package biz.phanithnhoem.exception;


import biz.phanithnhoem.base.ApiErrorResponse;
import biz.phanithnhoem.base.FieldError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse<Object> handleValidationException(MethodArgumentNotValidException ex){
        List<FieldError> errors = new ArrayList<>();
        ex.getFieldErrors().stream().forEach(fieldError -> {
            errors.add(FieldError.builder()
                    .message(fieldError.getDefaultMessage())
                    .field(fieldError.getField())
                    .build());
        });
        return ApiErrorResponse.builder()
                .status(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed. Please check your input and correct any errors. Ensure that all required fields are provided.")
                .errors(errors)
                .timestamp(Instant.now())
                .build();
    }

}
