package ru.practicum.exception;

import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;

@RestControllerAdvice()
@Generated
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final ValidationException e) {
        return new ApiError(
            e.getStackTrace(),
            e.getMessage(),
            "Incorrectly made request",
            HttpStatus.BAD_REQUEST,
            LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(final BadRequestException e) {
        return new ApiError(
                e.getStackTrace(),
                e.getMessage(),
                "Incorrectly made request",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
    }
}
