package ru.practicum.users.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.users.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAllUsers(List<Long> ids, PageRequest pageRequest);

    Optional<UserDto> createUser(UserDto userDto);

    void deleteUser(long userId);
}
