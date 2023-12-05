package biz.phanithnhoem.exception;

import biz.phanithnhoem.base.ApiErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class BusinessExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiErrorResponse<Object> handleProvidedIdNotExistsException(DataIntegrityViolationException ex) {
        return ApiErrorResponse.builder()
                .status(false)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Provided in doesn't exits in database.")
                .errors(ex.getMessage())
                .timestamp(Instant.now())
                .build();
    }
}
