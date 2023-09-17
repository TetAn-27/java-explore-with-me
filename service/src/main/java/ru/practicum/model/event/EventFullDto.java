package ru.practicum.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.model.user.UserDto;
import ru.practicum.model.category.CategoryDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    String annotation;
    CategoryDto category;
    int confirmedRequests;
    LocalDateTime createdOn;
    String description;
    LocalDateTime eventDate;
    Integer id;
    UserDto.UserShortDto initiator;
    Location location;
    boolean paid;
    int participantLimit;
    LocalDateTime publishedOn;
    boolean requestModeration;
    State state;
    String title;
    int views;
}