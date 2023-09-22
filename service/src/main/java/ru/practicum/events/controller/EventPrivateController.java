package ru.practicum.events.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.model.EventUpdateDto;
import ru.practicum.events.service.EventService;
import ru.practicum.events.model.EventDto;
import ru.practicum.events.model.EventFullDto;
import ru.practicum.events.model.EventShortDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class EventPrivateController {

    private final EventService eventService;

    public EventPrivateController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getAllEventsUser(@PathVariable(value = "userId") Long userId,
                                                @PositiveOrZero @RequestParam(value = "from", defaultValue = "0",
                                                        required = false) int page,
                                                @Positive @RequestParam(value = "size", defaultValue = "10",
                                                        required = false) int size) {
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.DESC, "start");
        return eventService.getAllEventsUser(userId, pageRequest);
    }

    @PostMapping("/{userId}/events")
    public ResponseEntity<EventFullDto> addEvent(@PathVariable(value = "userId") Long userId,
                                             @RequestBody  @Valid EventDto eventDto) {
        return ResponseEntity.of(eventService.addEvent(userId, eventDto));
    }

    @GetMapping("/{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> getEvent(@PathVariable(value = "userId") Long userId,
                                                 @PathVariable(value = "eventId") Long eventId) {
        return ResponseEntity.of(eventService.getEvent(userId, eventId));
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> updateEvent(@PathVariable(value = "userId") Long userId,
                                                    @PathVariable(value = "eventId") Long eventId,
                                                    @RequestBody  @Valid EventUpdateDto eventUpdateDto) {
        return ResponseEntity.of(eventService.updateEvent(userId, eventId, eventUpdateDto));
    }
}
