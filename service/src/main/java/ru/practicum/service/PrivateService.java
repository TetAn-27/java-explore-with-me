package ru.practicum.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.model.ParticipationRequestDto;
import ru.practicum.model.event.EventDto;
import ru.practicum.model.event.EventFullDto;
import ru.practicum.model.event.EventShortDto;

import java.util.List;
import java.util.Optional;

public interface PrivateService {

    List<EventShortDto> getAllEventsUser(long userId, PageRequest pageRequest);

    Optional<EventDto> addEvent(long userId, EventDto eventDto);

    Optional<EventFullDto> getEvent(long userId, long eventId);

    Optional<EventFullDto> updateEvent(long userId, long eventId, EventDto eventDto);

    List<ParticipationRequestDto> getRequestCurrentUser(long userId, long eventId);

    Optional<ParticipationRequestDto> updateRequestCurrentUser(long userId, long eventId, ParticipationRequestDto bodyDto);

    List<ParticipationRequestDto> getRequestOtherUser(long userId);

    Optional<ParticipationRequestDto> addRequest(long userId, long eventId);

    Optional<ParticipationRequestDto> cancelRequest(long userId, long requestId);
}
