package ru.practicum.compilations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.events.model.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private long id;
    private List<EventShortDto> events;
    private boolean pinned;
    @NotNull(message = "поле title является обязательным")
    @NotBlank(message = "Title не может состоять из пробелов")
    @NotEmpty(message = "Title не может быть пустым")
    @Size(min = 3, max = 50)
    private String title;
}
