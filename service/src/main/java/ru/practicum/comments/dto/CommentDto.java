package ru.practicum.comments.dto;

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
public class CommentDto {
    @NotNull(message = "поле text является обязательным")
    @NotBlank(message = "Text не может состоять из пробелов")
    @NotEmpty(message = "Text не может быть пустым")
    @Size(min = 3, max = 10000)
    private String text;
}
