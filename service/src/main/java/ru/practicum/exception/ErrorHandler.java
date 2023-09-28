package ru.practicum.exception;

import lombok.Generated;
import org.hibernate.JDBCException;
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        return new ApiError(
                e.getStackTrace(),
                e.getMessage(),
                "The required object was not found",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleServerErrorException(final ConflictException e) {
        return new ApiError(
                e.getStackTrace(),
                e.getMessage(),
                "Integrity constraint has been violated",
                HttpStatus.CONFLICT,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleJDBCException(final JDBCException e) {
        return new ApiError(
                e.getStackTrace(),
                e.getMessage(),
                "Integrity constraint has been violated",
                HttpStatus.CONFLICT,
                LocalDateTime.now()
        );
    }
}
