package ru.practicum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatDto {
    Integer id;
    String app;
    String uri;
    String ip;
    LocalDateTime timestamp;
}
