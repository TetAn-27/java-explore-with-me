package ru.practicum.events;

import org.springframework.data.domain.PageRequest;
import ru.practicum.events.model.EventDto;
import ru.practicum.events.model.EventFullDto;
import ru.practicum.events.model.EventShortDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EventService {

    List<EventShortDto> getAllEventsUser(long userId, PageRequest pageRequest);

    Optional<EventDto> addEvent(long userId, EventDto eventDto);

    Optional<EventFullDto> getEvent(long userId, long eventId);

    Optional<EventFullDto> updateEvent(long userId, long eventId, EventDto eventDto);

    List<EventFullDto> getAllEventsInfo(Map<String, ? extends List<? extends Serializable>> parameters,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, PageRequest pageRequest);

    List<EventFullDto> getEventInfo(long id);

    List<EventShortDto> getPublicEventsInfo(Map<String, Object> parameters, List<Long> categories,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd, PageRequest pageRequest);

    Optional<EventShortDto> getEventById(long id);
}
