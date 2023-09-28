package ru.practicum.categories.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private long id;
    @NotNull(message = "поле name является обязательным")
    @NotBlank(message = "Name не может состоять из пробелов")
    @NotEmpty(message = "Name не может быть пустым")
    @Size(min = 2, max = 50)
    private String name;
}
