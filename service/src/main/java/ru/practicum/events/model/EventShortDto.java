package ru.practicum.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.users.model.UserDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    String annotation;
    CategoryDto category;
    int confirmedRequests;
    LocalDateTime eventDate;
    Integer id;
    UserDto.UserShortDto initiator;
    boolean paid;
    String title;
    int views;
}
