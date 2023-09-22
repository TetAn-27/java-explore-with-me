package ru.practicum.events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.State;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Page<Event> findAllByInitiatorId(long id, Pageable page);
    Event save(Event event);
    Event getById(long Id);
    Page<Event> findByEventDateBetweenAndInitiatorIdInAndCategoryIdInAndState(LocalDateTime rangeStart,
                      LocalDateTime rangeEnd, Iterable userIds, Iterable catIds, State state, Pageable page);
    Page<Event> findByEventDateBetweenAndInitiatorIdInAndCategoryIdIn(LocalDateTime rangeStart,
                      LocalDateTime rangeEnd, Iterable userIds, Iterable catIds, Pageable page);
}
