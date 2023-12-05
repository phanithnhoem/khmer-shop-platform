package biz.phanithnhoem.exception;

import biz.phanithnhoem.base.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@RestControllerAdvice
public class ResponseStatusExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResponseStatusException.class)
    public ApiErrorResponse<Object> handleResponseStatusException(ResponseStatusException ex) {
        return ApiErrorResponse.builder()
                .status(false)
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getReason())
                .errors(ex.getMessage())
                .timestamp(Instant.now())
                .build();
    }
}
