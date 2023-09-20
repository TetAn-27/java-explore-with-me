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

    long id;
    @Email(message = "Некорректный email")
    @NotEmpty(message = "Email не может быть пустым")
    String email;
    String name;

    @Data
    @AllArgsConstructor
    public class UserShortDto {
        long id;
        String name;
    }
}
