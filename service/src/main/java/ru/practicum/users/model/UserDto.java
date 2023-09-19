package ru.practicum.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    long id;
    String email;
    String name;

    @Data
    @AllArgsConstructor
    public class UserShortDto {
        long id;
        String name;
    }
}
