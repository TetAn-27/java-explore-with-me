package ru.practicum.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    @Email(message = "Некорректный email")
    @NotEmpty(message = "Email не может быть пустым")
    @Size(min = 6, max = 254)
    private String email;
    @NotNull(message = "поле name является обязательным")
    @NotBlank(message = "Name не может состоять из пробелов")
    @NotEmpty(message = "Name не может быть пустым")
    @Size(min = 2, max = 250)
    private String name;

    @Data
    @AllArgsConstructor
    public static class UserShortDto {
        private long id;
        private String name;
    }
}
