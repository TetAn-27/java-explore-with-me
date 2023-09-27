package ru.practicum.users.model;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }

    public static UserDto.UserShortDto toUserShortDto(User user) {
        return new UserDto.UserShortDto(
                user.getId(),
                user.getName()
        );
    }

    public static User toUser(UserDto userDto) {
        return new User(
                0,
                userDto.getEmail(),
                userDto.getName()
        );
    }

    public static List<UserDto> toListUserDto(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(toUserDto(user));
        }
        return userDtoList;
    }
}
