package ru.practicum.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.model.category.CategoryDto;

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
