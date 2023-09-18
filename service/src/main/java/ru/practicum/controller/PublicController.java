package ru.practicum.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.CompilationDto;
import ru.practicum.model.category.CategoryDto;
import ru.practicum.model.event.EventShortDto;
import ru.practicum.service.PublicService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class PublicController {

    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @GetMapping("/compilations")
    public List<CompilationDto> getCollectionsEvents(@RequestParam(value = "pinned") boolean pinned,
                                                     @PositiveOrZero @RequestParam(value = "from", defaultValue = "0",
                                                        required = false) int page,
                                                     @Positive @RequestParam(value = "size", defaultValue = "10",
                                                        required = false) int size) {
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.DESC, "id");
        return publicService.getCollectionsEvents(pinned, pageRequest);
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<CompilationDto> getCompilationById(@PathVariable(value = "compId") Long compId) {
        return ResponseEntity.of(publicService.getCompilationById(compId));
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategory(@PositiveOrZero @RequestParam(value = "from", defaultValue = "0", required = false)
                                                int page,
                                            @Positive @RequestParam(value = "size", defaultValue = "10", required = false)
                                            int size) {
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.DESC, "id");
        return publicService.getAllCategory(pageRequest);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(value = "catId") Long catId) {
        return ResponseEntity.of(publicService.getCategoryById(catId));
    }

    @GetMapping("/events")
    public List<EventShortDto> getPublicEventsInfo(@RequestParam(value = "text", required = false) String text,
                                                   @RequestParam(value = "categories", required = false) List<Long> categories,
                                                   @RequestParam(value = "paid", required = false) boolean paid,
                                                   @RequestParam(value = "rangeStart") @NotNull
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                   @RequestParam(value = "rangeEnd") @NotNull
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                   @RequestParam(value = "isAvailable", required = false,
                                                        defaultValue = "false") boolean isAvailable,
                                                   @RequestParam(value = "sort", required = false) String sort, //EVENT_DATE, VIEWS
                                                   @PositiveOrZero  @RequestParam(value = "from", defaultValue = "0",
                                                       required = false) int page,
                                                   @Positive @RequestParam(value = "size", defaultValue = "10",
                                                       required = false) int size) {
        Map<String, Object> parameters = Map.of(
                "text", text,
                "paid", paid,
                "isAvailable", isAvailable
        );
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.DESC, "start");
        return publicService.getPublicEventsInfo(parameters, categories, rangeStart, rangeEnd, pageRequest);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventShortDto> getEventById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.of(publicService.getEventById(id));
    }
}
