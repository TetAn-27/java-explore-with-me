package ru.practicum.events.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.categories.CategoryRepository;
import ru.practicum.categories.model.Category;
import ru.practicum.events.EventRepository;
import ru.practicum.events.model.*;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.users.UserRepository;
import ru.practicum.users.model.User;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
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
            return EventMapper.toListEventShortDto(pageRequest.getContent());
        } while (page != null);
    }

    @Override
    public Optional<EventFullDto> addEvent(long userId, EventDto eventDto) {
        eventDto.setCreatedOn(LocalDateTime.now());
        Category category = categoryRepository.getCategoryById(eventDto.getCategoryId());
        User user = userRepository.getById(userId);
        long views = 0;
        Event event = EventMapper.toEvent(eventDto, category, user, views);
        log.debug("Событие создано: {}", event);
        return Optional.of(EventMapper.toEventFullDto(eventRepository.save(event)));
    }

    @Override
    public Optional<EventFullDto> getEvent(long userId, long eventId) {
        try {
            Event event = eventRepository.getById(eventId);
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
            throw new ConflictException("Событие уже опубликовано. Изменение события невозмлжно.");
        }
        if ((eventUpdateDto == null && eventOld.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) ||
                Objects.requireNonNull(eventUpdateDto).getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("Начало события должно быть спустя два часа от текущего врмени");
        }
        Event event = getUpdateEvent(eventOld, eventUpdateDto);
        return Optional.of(EventMapper.toEventFullDto(eventRepository.save(event)));
    }

    @Override
    public List<EventFullDto> getAllEventsInfo(Map<String, ? extends List<? extends Serializable>> parameters, LocalDateTime rangeStart, LocalDateTime rangeEnd, PageRequest pageRequestMethod) {
        Pageable page = pageRequestMethod;
        do {
            Page<Event> pageRequest;
            if (String.valueOf(parameters.get("states")).equals("ALL")) {
                pageRequest = eventRepository.findByEventDateBetweenAndInitiatorIdInAndCategoryIdIn(rangeStart, rangeEnd,parameters.get("users"),
                        parameters.get("categories"), page);
            } else {
                pageRequest = eventRepository.findByEventDateBetweenAndInitiatorIdInAndCategoryIdInAndState(rangeStart, rangeEnd,parameters.get("users"),
                    parameters.get("categories"), State.valueOf(String.valueOf(parameters.get("states"))), page);
            }
            pageRequest.getContent().forEach(ItemRequest -> {
            });
            if (pageRequest.hasNext()) {
                page = PageRequest.of(pageRequest.getNumber() + 1, pageRequest.getSize(), pageRequest.getSort());
            } else {
                page = null;
            }
            return EventMapper.toListEventFullDto(pageRequest.getContent());
            //EventMapper.toListEventFullDto(getListAccordingState(pageRequest.getContent(), String.valueOf(parameters.get("states")), rangeStart, rangeEnd));
        } while (page != null);
    }

    @Override
    public Optional<EventFullDto> updateEventAdmin(long eventId, EventUpdateDto eventUpdateDto) {
        Event eventOld = eventRepository.getById(eventId);
        if (eventOld.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Событие уже опубликовано. Изменение события невозмлжно.");
        }
        Event event = getUpdateEvent(eventOld, eventUpdateDto);
        return Optional.of(EventMapper.toEventFullDto(eventRepository.save(event)));
    }

    @Override
    public List<EventShortDto> getPublicEventsInfo(Map<String, Object> parameters, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Optional<EventShortDto> getEventById(long id) {
        return Optional.empty();
    }

    private Event getUpdateEvent(Event event, EventUpdateDto eventUpdate) {
        event.setEventDate(eventUpdate.getEventDate() != null ? eventUpdate.getEventDate() : event.getEventDate());
        event.setAnnotation(eventUpdate.getAnnotation() != null ? eventUpdate.getAnnotation() : event.getAnnotation());
        event.setCategory(eventUpdate.getCategoryId() != null ? categoryRepository.getCategoryById(eventUpdate.getCategoryId()) : event.getCategory());
        event.setDescription(eventUpdate.getDescription() != null ? eventUpdate.getDescription() : event.getDescription());
        event.setLocation(eventUpdate.getLocation() != null ? eventUpdate.getLocation() : event.getLocation());
        event.setPaid(eventUpdate.getPaid() != null ? eventUpdate.getPaid() : event.isPaid());
        event.setParticipantLimit(eventUpdate.getParticipantLimit() != null ? eventUpdate.getParticipantLimit() : event.getParticipantLimit());
        event.setRequestModeration(eventUpdate.getRequestModeration() != null ? eventUpdate.getRequestModeration() : event.isRequestModeration());
        event.setState(eventUpdate.getStateAction() != null ? eventUpdate.getStateAction() : event.getState());
        event.setTitle(eventUpdate.getTitle() != null ? eventUpdate.getTitle() : event.getTitle());
        return event;
    }

    /*private List<Event> getListAccordingState(List<Event> eventList, String stat, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        switch (stat) {
            case "ALL":
                return checkingStatus(eventList, stat, rangeStart, rangeEnd);
            case "PENDING":
                return checkingStatus(eventList, stat, rangeStart, rangeEnd);
            case "PUBLISHED":
                return checkingStatus(eventList, stat, rangeStart, rangeEnd);
            case "CANCELED":
                return checkingStatus(eventList, stat, rangeStart, rangeEnd);
            default:
                throw new StateException("Был указан неверный параметр фильтрации");
        }
    }

    private List<Event> checkingStatus(List<Event> eventList, String stat, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        return eventList.stream()
                .filter(i -> !i.getState().equals(State.CANCELED)
                        && (i.getStart().equals(LocalDateTime.now())
                        || i.getStart().isBefore(LocalDateTime.now()))
                        && (i.getEnd().equals(LocalDateTime.now())
                        || i.getEnd().isAfter(LocalDateTime.now())))
                .collect(Collectors.toList());
    }*/
}
