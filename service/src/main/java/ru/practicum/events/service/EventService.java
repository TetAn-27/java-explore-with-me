package ru.practicum.events.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.events.model.EventDto;
import ru.practicum.events.model.EventFullDto;
import ru.practicum.events.model.EventShortDto;
import ru.practicum.events.model.EventUpdateDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EventService {

    List<EventShortDto> getAllEventsUser(long userId, PageRequest pageRequest);

    Optional<EventFullDto> addEvent(long userId, EventDto eventDto);

    Optional<EventFullDto> getEvent(long userId, long eventId);

    Optional<EventFullDto> updateEvent(long userId, long eventId, EventUpdateDto eventUpdateDto);

    List<EventFullDto> getAllEventsInfo(Map<String, Object>  parameters, PageRequest pageRequest);

    Optional<EventFullDto> updateEventAdmin(long eventId, EventUpdateDto eventUpdateDto);

    List<EventShortDto> getPublicEventsInfo(Map<String, Object> parameters, PageRequest pageRequest);

    Optional<EventFullDto> getEventById(long id);
}
