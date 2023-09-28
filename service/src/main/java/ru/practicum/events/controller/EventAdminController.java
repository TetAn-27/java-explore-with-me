package ru.practicum.events.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.model.EventUpdateDto;
import ru.practicum.events.model.State;
import ru.practicum.events.service.EventService;
import ru.practicum.events.model.EventFullDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/admin/events")
@Valid
public class EventAdminController {

    private final EventService eventService;

    public EventAdminController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public List<EventFullDto> getAllEventsInfo(@RequestParam(value = "users", required = false) List<Long> users,
                                               @RequestParam(value = "states", required = false) List<State> states,
                                               @RequestParam(value = "categories", required = false) List<Long> categories,
                                               @RequestParam(value = "rangeStart", required = false)
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                               @RequestParam(value = "rangeEnd", required = false)
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                               @PositiveOrZero @RequestParam(value = "from", defaultValue = "0",
                                                       required = false) int page,
                                               @Positive @RequestParam(value = "size", defaultValue = "10",
                                                       required = false) int size) {
        Map<String, Object> parameters = new HashMap<>();
                parameters.put("users", users);
                parameters.put("states", states);
                parameters.put("categories", categories);
                parameters.put("rangeStart", rangeStart);
                parameters.put("rangeEnd", rangeEnd);
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.ASC, "id");
        return eventService.getAllEventsInfo(parameters, pageRequest);
    }

    @PatchMapping("/{eventId}")
    @Valid
    public ResponseEntity<EventFullDto> updateEventAdmin(@PathVariable(value = "eventId") Long eventId,
                                                         @RequestBody @Valid EventUpdateDto eventUpdateDto) {
        return ResponseEntity.of(eventService.updateEventAdmin(eventId, eventUpdateDto));
    }
}
