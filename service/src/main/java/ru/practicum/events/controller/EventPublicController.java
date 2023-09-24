package ru.practicum.events.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.model.EventFullDto;
import ru.practicum.events.service.EventService;
import ru.practicum.events.model.EventShortDto;
import ru.practicum.exception.ConflictException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.HashMap;
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
                                                   @RequestParam(value = "rangeStart", required = false)
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                   @RequestParam(value = "rangeEnd", required = false)
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                   @RequestParam(value = "onlyAvailable", required = false,
                                                        defaultValue = "false") boolean onlyAvailable,
                                                   @RequestParam(value = "sort", required = false) String sort,
                                                   @PositiveOrZero  @RequestParam(value = "from", defaultValue = "0",
                                                       required = false) int page,
                                                   @Positive @RequestParam(value = "size", defaultValue = "10",
                                                       required = false) int size) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("text", text);
        parameters.put("categories", categories);
        parameters.put("paid", paid);
        parameters.put("rangeStart", rangeStart);
        parameters.put("rangeEnd", rangeEnd);
        parameters.put("isAvailable", onlyAvailable);
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.ASC, determineSort(sort));
        return eventService.getPublicEventsInfo(parameters, pageRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventFullDto> getEventById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.of(eventService.getEventById(id));
    }

    private String determineSort(String sort) {
        if (sort == null) {
            return "id";
        }
        switch (sort) {
            case "EVENT_DATE":
                return "eventDate";
            case "VIEWS":
                return "views";
            default:
                throw new ConflictException("Был указан неверный параметр сортировки данных");
        }
    }
}
