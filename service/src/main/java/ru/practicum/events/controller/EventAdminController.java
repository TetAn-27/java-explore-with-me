package ru.practicum.events.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.model.EventUpdateDto;
import ru.practicum.events.service.EventService;
import ru.practicum.events.model.EventFullDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/admin/events")
public class EventAdminController {

    private final EventService eventService;

    public EventAdminController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public List<EventFullDto> getAllEventsInfo(@RequestParam(value = "users", required = false) List<Long> users,
                                               @RequestParam(value = "states", defaultValue = "ALL",
                                                       required = false) List<String> states,
                                               @RequestParam(value = "categories", required = false) List<Long> categories,
                                               @RequestParam(value = "rangeStart") @NotNull
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                               @RequestParam(value = "rangeEnd") @NotNull
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                               @PositiveOrZero @RequestParam(value = "from", defaultValue = "0",
                                                       required = false) int page,
                                               @Positive @RequestParam(value = "size", defaultValue = "10",
                                                       required = false) int size) {
        Map<String, ? extends List<? extends Serializable>> parameters = Map.of(
                "users", users,
                "states", states,
                "categories", categories
        );
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.DESC, "start");
        return eventService.getAllEventsInfo(parameters, rangeStart, rangeEnd, pageRequest);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEventAdmin(@PathVariable(value = "eventId") Long eventId,
                                                         @RequestBody EventUpdateDto eventUpdateDto) {
        return ResponseEntity.of(eventService.updateEventAdmin(eventId, eventUpdateDto));
    }
}
