package ru.practicum.events.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.EventService;
import ru.practicum.events.model.EventShortDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/events")
public class EventPublicController {

    private final EventService eventService;

    public EventPublicController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
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
        return eventService.getPublicEventsInfo(parameters, categories, rangeStart, rangeEnd, pageRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventShortDto> getEventById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.of(eventService.getEventById(id));
    }
}
