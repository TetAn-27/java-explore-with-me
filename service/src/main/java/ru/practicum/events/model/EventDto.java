package ru.practicum.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.categories.model.CategoryDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    String annotation;
    CategoryDto category;
    String description;
    LocalDateTime eventDate;
    Location location;
    boolean paid;
    int participantLimit;
    boolean requestModeration;
    String title;
}
