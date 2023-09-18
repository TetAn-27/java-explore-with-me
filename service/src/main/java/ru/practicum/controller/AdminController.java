package ru.practicum.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.AdminService;
import ru.practicum.model.CompilationDto;
import ru.practicum.model.user.UserDto;
import ru.practicum.model.category.CategoryDto;
import ru.practicum.model.event.EventFullDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> addNewCategory(@RequestBody  @Valid CategoryDto categoryDto) {
        return ResponseEntity.of(adminService.addNewCategory(categoryDto));
    }

    @PatchMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("catId") Long catId,
                                                      @RequestBody  @Valid CategoryDto categoryDto) {
        return ResponseEntity.of(adminService.updateCategory(catId, categoryDto));
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategory(@PathVariable("catId") Long catId) {
        adminService.deleteCategory(catId);
    }

    @GetMapping("/events")
    public List<EventFullDto> getAllEventsInfo(@RequestParam(value = "users", required = false) List<Long> users,
                                               @RequestParam(value = "states", defaultValue = "ALL",
                                                                    required = false) List<String> states,
                                               @RequestParam(value = "categories", required = false) List<Long> categories,
                                               @RequestParam(value = "rangeStart") @NotNull
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                               @RequestParam(value = "rangeEnd") @NotNull
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                               @PositiveOrZero  @RequestParam(value = "from", defaultValue = "0",
                                                       required = false) int page,
                                               @Positive @RequestParam(value = "size", defaultValue = "10",
                                                       required = false) int size) {
        Map<String, ? extends List<? extends Serializable>> parameters = Map.of(
                "users", users,
                "states", states,
                "categories", categories
        );
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.DESC, "start");
        return adminService.getAllEventsInfo(parameters, rangeStart, rangeEnd, pageRequest);
    }

    @GetMapping("/events/{id}")
    public List<EventFullDto> getEventInfo(@PathVariable("id") Long id) {
        return adminService.getEventInfo(id);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers(@RequestParam(value = "ids", required = false) List<Long> ids,
                                     @PositiveOrZero @RequestParam(value = "from", defaultValue = "0",
                                             required = false) int page,
                                     @Positive @RequestParam(value = "size", defaultValue = "10",
                                             required = false) int size) {
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.DESC, "start");
        return adminService.getAllUsers(ids, pageRequest);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody  @Valid UserDto userDto) {
        return ResponseEntity.of(adminService.createUser(userDto));
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        adminService.deleteUser(userId);
    }

    @PostMapping("/compilations")
    public ResponseEntity<CompilationDto> addCompilation(@RequestBody  @Valid CompilationDto compilationDto) {
        return ResponseEntity.of(adminService.addCompilation(compilationDto));
    }

    @DeleteMapping("/compilation/{compId}")
    public void deleteCompilation(@PathVariable("compId") Long compId) {
        adminService.deleteCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(@PathVariable("compId") Long compId,
                                                            @RequestBody  @Valid CompilationDto compilationDto) {
        return ResponseEntity.of(adminService.updateCompilation(compId, compilationDto));
    }
}
