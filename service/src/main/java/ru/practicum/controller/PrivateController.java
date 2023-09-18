package ru.practicum.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.ParticipationRequestDto;
import ru.practicum.model.event.EventFullDto;
import ru.practicum.model.event.EventShortDto;
import ru.practicum.model.event.EventDto;
import ru.practicum.service.PrivateService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class PrivateController {

    private final PrivateService privateService;

    public PrivateController(PrivateService privateService) {
        this.privateService = privateService;
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getAllEventsUser(@PathVariable(value = "userId") Long userId,
                                                @PositiveOrZero  @RequestParam(value = "from", defaultValue = "0",
                                             required = false) int page,
                                                @Positive @RequestParam(value = "size", defaultValue = "10",
                                             required = false) int size) {
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.DESC, "start");
        return privateService.getAllEventsUser(userId, pageRequest);
    }

    @PostMapping("/{userId}/events")
    public ResponseEntity<EventDto> addEvent(@PathVariable(value = "userId") Long userId,
                                             @RequestBody  @Valid EventDto eventDto) {
        return ResponseEntity.of(privateService.addEvent(userId, eventDto));
    }

    @GetMapping("/{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> getEvent(@PathVariable(value = "userId") Long userId,
                                                 @PathVariable(value = "eventId") Long eventId) {
        return ResponseEntity.of(privateService.getEvent(userId, eventId));
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> updateEvent(@PathVariable(value = "userId") Long userId,
                                                    @PathVariable(value = "eventId") Long eventId,
                                                    @RequestBody  @Valid EventDto eventDto) {
        return ResponseEntity.of(privateService.updateEvent(userId, eventId, eventDto));
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestCurrentUser(@PathVariable(value = "userId") Long userId,
                                                               @PathVariable(value = "eventId") Long eventId) {
        return privateService.getRequestCurrentUser(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<ParticipationRequestDto> updateRequestCurrentUser(@PathVariable(value = "userId") Long userId,
                                                                            @PathVariable(value = "eventId") Long eventId,
                                                                            @RequestBody  @Valid ParticipationRequestDto bodyDto) {
        return ResponseEntity.of(privateService.updateRequestCurrentUser(userId, eventId, bodyDto));
    }

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequestOtherUser(@PathVariable(value = "userId") Long userId) {
        return privateService.getRequestOtherUser(userId);
    }

    @PostMapping("/{userId}/requests")
    public ResponseEntity<ParticipationRequestDto> addRequest(@PathVariable(value = "userId") Long userId,
                                                              @RequestParam(value = "eventId") Long eventId) {
        return ResponseEntity.of(privateService.addRequest(userId, eventId));
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequest(@PathVariable(value = "userId") Long userId,
                                                                 @PathVariable(value = "requestId") Long requestId) {
        return ResponseEntity.of(privateService.cancelRequest(userId, requestId));
    }
}
