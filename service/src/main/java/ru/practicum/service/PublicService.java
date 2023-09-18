package ru.practicum.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.model.CompilationDto;
import ru.practicum.model.category.CategoryDto;
import ru.practicum.model.event.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PublicService {

    List<CompilationDto> getCollectionsEvents(boolean pinned, PageRequest pageRequest);

    Optional<CompilationDto> getCompilationById(long compId);

    List<CategoryDto> getAllCategory(PageRequest pageRequest);

    Optional<CategoryDto> getCategoryById(long catId);

    List<EventShortDto> getPublicEventsInfo(Map<String, Object> parameters, List<Long> categories,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd, PageRequest pageRequest);

    Optional<EventShortDto> getEventById(long id);
}
