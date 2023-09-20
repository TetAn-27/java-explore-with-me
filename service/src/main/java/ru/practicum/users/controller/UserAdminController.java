package ru.practicum.users.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.users.model.UserDto;
import ru.practicum.users.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/users")
public class UserAdminController {

    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<UserDto> getAllUsers(@RequestParam(value = "ids", required = false) List<Long> ids,
                                     @PositiveOrZero @RequestParam(value = "from", defaultValue = "0",
                                             required = false) int page,
                                     @Positive @RequestParam(value = "size", defaultValue = "10",
                                             required = false) int size) {
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.DESC, "start");
        return userService.getAllUsers(ids, pageRequest);
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody  @Valid UserDto userDto) {
        return ResponseEntity.of(userService.createUser(userDto));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}
