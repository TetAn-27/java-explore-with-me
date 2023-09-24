package ru.practicum.events.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.categories.model.CategoryDto;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    @NotNull(message = "поле annotation является обязательным")
    @NotBlank(message = "Annotation не может состоять из пробелов")
    @NotEmpty(message = "Annotation не может быть пустым")
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Long category;
    private Long confirmedRequests;
    private LocalDateTime createdOn;
    @NotNull(message = "поле description является обязательным")
    @NotBlank(message = "Description не может состоять из пробелов")
    @NotEmpty(message = "Description не может быть пустым")
    @Size(min = 20, max = 7000)
    private String description;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent
    private LocalDateTime eventDate;
    @NotNull
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    @NotNull(message = "поле title является обязательным")
    @NotBlank(message = "Title не может состоять из пробелов")
    @NotEmpty(message = "Title не может быть пустым")
    @Size(min = 3, max = 120)
    private String title;
}
