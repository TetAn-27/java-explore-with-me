package ru.practicum.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.categories.model.CategoryDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    @NotNull
    private String annotation;
    @NotNull
    private long categoryId;
    private long confirmedRequests;
    private LocalDateTime createdOn;
    @NotNull
    private String description;
    @NotNull
    private LocalDateTime eventDate;
    @NotNull
    private Location location;
    private boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private State state;
    @NotNull
    private String title;
}
