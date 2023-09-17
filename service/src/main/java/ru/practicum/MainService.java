package ru.practicum;

import org.springframework.data.domain.PageRequest;
import ru.practicum.model.CompilationDto;
import ru.practicum.model.event.EventFullDto;
import ru.practicum.model.user.UserDto;
import ru.practicum.model.category.CategoryDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MainService {
    Optional<CategoryDto> addNewCategory(CategoryDto categoryDto);

    Optional<CategoryDto> updateCategory(long catId, CategoryDto categoryDto);

    void deleteCategory(long catId);

    public List<EventFullDto> getAllEventsInfo(Map<String, ? extends List<? extends Serializable>> parameters,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, PageRequest pageRequest);

    List<EventFullDto> getEventInfo(long id);

    List<UserDto> getAllUsers(List<Long> ids, PageRequest pageRequest);

    Optional<UserDto> createUser(UserDto userDto);

    void deleteUser(long userId);

    Optional<CompilationDto> addCompilation(CompilationDto compilationDto);

    void deleteCompilation(Long compId);

    Optional<CompilationDto> updateCompilation(Long compId, CompilationDto compilationDto);
}
