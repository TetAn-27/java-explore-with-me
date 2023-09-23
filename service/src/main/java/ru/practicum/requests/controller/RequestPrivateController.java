package ru.practicum.requests.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.requests.model.EventRequestStatus;
import ru.practicum.requests.model.EventRequestStatusUpdate;
import ru.practicum.requests.model.ParticipationRequestDto;
import ru.practicum.requests.service.RequestService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class RequestPrivateController {

    private final RequestService requestService;

    public RequestPrivateController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestCurrentUser(@PathVariable(value = "userId") Long userId,
                                                               @PathVariable(value = "eventId") Long eventId) {
        return requestService.getRequestCurrentUser(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<EventRequestStatus> updateRequestCurrentUser(@PathVariable(value = "userId") Long userId,
                                                                       @PathVariable(value = "eventId") Long eventId,
                                                                       @RequestBody  @Valid EventRequestStatusUpdate bodyDto) {
        return ResponseEntity.of(requestService.updateRequestCurrentUser(userId, eventId, bodyDto));
    }

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequestOtherUser(@PathVariable(value = "userId") Long userId) {
        return requestService.getRequestOtherUser(userId);
    }

    @PostMapping("/{userId}/requests")
    public ResponseEntity<ParticipationRequestDto> addRequest(@PathVariable(value = "userId") Long userId,
                                                              @RequestParam(value = "eventId") Long eventId) {
        return ResponseEntity.of(requestService.addRequest(userId, eventId));
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequest(@PathVariable(value = "userId") Long userId,
                                                                 @PathVariable(value = "requestId") Long requestId) {
        return ResponseEntity.of(requestService.cancelRequest(userId, requestId));
    }
}
