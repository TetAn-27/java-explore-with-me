package ru.practicum.requests.service;

import ru.practicum.requests.model.EventRequestStatus;
import ru.practicum.requests.model.EventRequestStatusUpdate;
import ru.practicum.requests.model.ParticipationRequestDto;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    List<ParticipationRequestDto> getRequestCurrentUser(long userId, long eventId);

    Optional<EventRequestStatus> updateRequestCurrentUser(long userId, long eventId, EventRequestStatusUpdate bodyDto);

    List<ParticipationRequestDto> getRequestOtherUser(long userId);

    Optional<ParticipationRequestDto> addRequest(long userId, long eventId);

    Optional<ParticipationRequestDto> cancelRequest(long userId, long requestId);
}
