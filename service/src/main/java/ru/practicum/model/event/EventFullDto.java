package ru.practicum.model.event;

import ru.practicum.model.user.UserDto;
import ru.practicum.model.category.CategoryDto;

import java.time.LocalDateTime;

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
