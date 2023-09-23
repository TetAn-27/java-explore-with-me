package ru.practicum.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdateDto {
    @Size(min = 20, max = 2000)
    private String annotation;
    private Long categoryId;
    @Size(min = 20, max = 7000)
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private State stateAction;
    @Size(min = 3, max = 120)
    private String title;
}
