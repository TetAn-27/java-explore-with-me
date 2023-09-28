package ru.practicum.events.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHit;
import ru.practicum.StatClient;
import ru.practicum.ViewStats;
import ru.practicum.categories.CategoryRepository;
import ru.practicum.categories.model.Category;
import ru.practicum.events.EventRepository;
import ru.practicum.events.LocationRepository;
import ru.practicum.events.model.*;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.users.UserRepository;
import ru.practicum.users.model.User;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final StatClient statClient;


    public EventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, UserRepository userRepository, LocationRepository locationRepository, StatClient statClient) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.statClient = statClient;
    }
    @Override
    public List<EventShortDto> getAllEventsUser(long userId, PageRequest pageRequestMethod) {
        Pageable page = pageRequestMethod;
        do {
            Page<Event> pageRequest = eventRepository.findAllByInitiatorId(userId, page);
            pageRequest.getContent().forEach(ItemRequest -> {
            });
            if (pageRequest.hasNext()) {
                page = PageRequest.of(pageRequest.getNumber() + 1, pageRequest.getSize(), pageRequest.getSort());
            } else {
                page = null;
            }
            List<Event> events = pageRequest.getContent();
            for (Event event : events) {
                getStatsClient(event);
            }
            return EventMapper.toListEventShortDto(events);
        } while (page != null);
    }

    @Override
    public Optional<EventFullDto> addEvent(long userId, EventDto eventDto) {
        eventDto.setCreatedOn(LocalDateTime.now());
        Category category = categoryRepository.getCategoryById(eventDto.getCategory());
        User user = userRepository.getById(userId);
        long views = 0;
        Event event = EventMapper.toEvent(eventDto, category, user, views);
        event.setState(State.PENDING);
        locationRepository.save(event.getLocation());
        log.debug("Событие создано: {}", event);
        return Optional.of(EventMapper.toEventFullDto(eventRepository.save(event)));
    }

    @Override
    public Optional<EventFullDto> getEvent(long userId, long eventId) {
        try {
            Event event = eventRepository.getById(eventId);
            getStatsClient(event);
            return Optional.of(EventMapper.toEventFullDto(eventRepository.save(event)));
        } catch (EntityNotFoundException ex) {
            log.error("События с Id {} не найдено", eventId);
            throw new NotFoundException("События с таким Id не было найдено");
        }
    }

    @Override
    public Optional<EventFullDto> updateEvent(long userId, long eventId, EventUpdateDto eventUpdateDto) {
        Event eventOld = eventRepository.getById(eventId);
        if (eventOld.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Событие уже опубликовано. Изменение события невозможно.");
        }
        if (eventUpdateDto.getEventDate() != null && eventUpdateDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("Начало события должно быть не раньше, чем за два часа от текущего врмени");
        }
        Event event = getUpdateEvent(eventOld, eventUpdateDto);
        return Optional.of(EventMapper.toEventFullDto(eventRepository.save(event)));
    }

    @Override
    public List<EventFullDto> getAllEventsInfo(Map<String, Object> parameters, PageRequest pageRequestMethod) {
        Pageable page = pageRequestMethod;
        LocalDateTime rangeStart = parameters.get("rangeStart") != null ? (LocalDateTime) parameters.get("rangeStart") : LocalDateTime.now();
        LocalDateTime rangeEnd = parameters.get("rangeEnd") != null ? (LocalDateTime) parameters.get("rangeEnd") : rangeStart.plusYears(1);
        if (rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("Начало события не может быть после конца события");
        }
        do {
            Page<Event> pageRequest = eventRepository.getAllEventsInfo((List<Long>) parameters.get("users"),
                    (List<State>) parameters.get("states"), (List<Long>) parameters.get("categories"), rangeStart, rangeEnd, page);
            if (pageRequest.hasNext()) {
                page = PageRequest.of(pageRequest.getNumber() + 1, pageRequest.getSize(), pageRequest.getSort());
            } else {
                page = null;
            }
            List<Event> events = pageRequest.getContent();
            for (Event event : events) {
                getStatsClient(event);
            }
            return EventMapper.toListEventFullDto(events);
        } while (page != null);
    }

    @Override
    public Optional<EventFullDto> updateEventAdmin(long eventId, EventUpdateDto eventUpdateDto) {
        Event eventOld = eventRepository.getById(eventId);
        if ((eventUpdateDto.getEventDate() != null && eventUpdateDto.getEventDate().isBefore(LocalDateTime.now().minusHours(1)))) {
            throw new ConflictException("Начало события должно быть не раньше, чем за час от даты публикации");
        }
        Event event = getUpdateEvent(eventOld, eventUpdateDto);
        return Optional.of(EventMapper.toEventFullDto(eventRepository.save(event)));
    }

    @Override
    public List<EventShortDto> getPublicEventsInfo(Map<String, Object> parameters, PageRequest pageRequestMethod,
                                                   HttpServletRequest request) {
        Pageable page = pageRequestMethod;
        LocalDateTime rangeStart = parameters.get("rangeStart") != null ? (LocalDateTime) parameters.get("rangeStart") : LocalDateTime.now();
        LocalDateTime rangeEnd = parameters.get("rangeEnd") != null ? (LocalDateTime) parameters.get("rangeEnd") : rangeStart.plusYears(1);
        List<Event> eventsAll = eventRepository.findAll();
        if (rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("Начало события не может быть после конца события");
        }
        do {
            Page<Event> pageRequest = eventRepository.getPublicEventsInfo((String) parameters.get("text"),
                    (List<Integer>) parameters.get("categories"), (Boolean) parameters.get("paid"), rangeStart,
                    rangeEnd, (Boolean) parameters.get("onlyAvailable"), page);
            if (pageRequest.hasNext()) {
                page = PageRequest.of(pageRequest.getNumber() + 1, pageRequest.getSize(), pageRequest.getSort());
            } else {
                page = null;
            }
            List<Event> events = pageRequest.getContent();
            addHitClient(request.getRequestURI(), request.getRemoteAddr());
            for (Event event : events) {
                getStatsClient(event);
            }
            return EventMapper.toListEventShortDto(events);
        } while (page != null);
    }

    @Override
    public Optional<EventFullDto> getEventById(long id, HttpServletRequest request) {
        Event event = eventRepository.getByIdAndState(id, State.PUBLISHED);
        if (event == null) {
            log.error("События с Id {} не найдено", id);
            throw new NotFoundException("События с таким Id не было найдено");
        }
        addHitClient(request.getRequestURI(), request.getRemoteAddr());
        getStatsClient(event);
        return Optional.of(EventMapper.toEventFullDto(event));
    }

    private Event getUpdateEvent(Event event, EventUpdateDto eventUpdate) {
        event.setEventDate(eventUpdate.getEventDate() != null ? eventUpdate.getEventDate() : event.getEventDate());
        event.setAnnotation(eventUpdate.getAnnotation() != null ? eventUpdate.getAnnotation() : event.getAnnotation());
        event.setCategory(eventUpdate.getCategory() != null ? categoryRepository.getCategoryById(eventUpdate.getCategory()) : event.getCategory());
        event.setDescription(eventUpdate.getDescription() != null ? eventUpdate.getDescription() : event.getDescription());
        event.setPaid(eventUpdate.getPaid() != null ? eventUpdate.getPaid() : event.isPaid());
        event.setParticipantLimit(eventUpdate.getParticipantLimit() != null ? eventUpdate.getParticipantLimit() : event.getParticipantLimit());
        event.setRequestModeration(eventUpdate.getRequestModeration() != null ? eventUpdate.getRequestModeration() : event.isRequestModeration());
        event.setTitle(eventUpdate.getTitle() != null ? eventUpdate.getTitle() : event.getTitle());
        if (eventUpdate.getLocation() != null) {
            Location newLocation = eventUpdate.getLocation();
            Location oldLocation = event.getLocation();
            oldLocation.setLat(newLocation.getLat());
            oldLocation.setLon(newLocation.getLon());
            locationRepository.save(oldLocation);
        }
        if (eventUpdate.getStateAction() != null) {
            checkStateAction(eventUpdate.getStateAction(), event);
        }
        return event;
    }

    private void checkStateAction(StateAction action, Event event) {
        switch (action) {
            case SEND_TO_REVIEW:
                event.setState(State.PENDING);
                break;
            case CANCEL_REVIEW:
                event.setState(State.CANCELED);
                break;
            case REJECT_EVENT:
                if (event.getState().equals(State.PUBLISHED)) {
                    throw new ConflictException("Событие уже опубликовано. Изменение события невозможно.");
                }
                event.setState(State.CANCELED);
                break;
            case PUBLISH_EVENT:
                if (!event.getState().equals(State.PENDING)) {
                    throw new ConflictException("Событие можно опубликовать только если оно находится в состоянии ожидания");
                }
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
                break;
        }
    }

    private void addHitClient(String uri, String ip) {
        statClient.addHit(new EndpointHit(
                null,
                "ewm-main-service",
                uri,
                ip,
                LocalDateTime.now()
        ));
    }

    private void getStatsClient(Event event) {
        LocalDateTime start = event.getCreatedOn();
        LocalDateTime end = LocalDateTime.now();
        List<ViewStats> stats = statClient.getStats(start, end, List.of("/events/" + event.getId()), true);
        event.setViews(stats.size() == 0 ? 0L : stats.get(0).getHits());
    }
}
