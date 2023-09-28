package ru.practicum.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private StackTraceElement[] errors;
    private String message;
    private String reason;
    private HttpStatus status;
    private LocalDateTime timestamp;
}
