package ru.practicum.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    @Email(message = "Некорректный email")
    @NotEmpty(message = "Email не может быть пустым")
    private String email;
    private String name;

    @Data
    @AllArgsConstructor
    public static class UserShortDto {
        private long id;
        private String name;
    }
}
