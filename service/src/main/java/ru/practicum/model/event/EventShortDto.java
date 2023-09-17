package ru.practicum.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.model.category.CategoryDto;
import ru.practicum.model.user.UserDto;

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
