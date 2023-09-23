package ru.practicum.events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAllByInitiatorId(long id, Pageable page);

    Event save(Event event);

    Event getById(long Id);

    Page<Event> findByEventDateBetweenAndInitiatorIdInAndCategoryIdInAndState(LocalDateTime rangeStart,
                      LocalDateTime rangeEnd, Iterable userIds, Iterable catIds, State state, Pageable page);

    Page<Event> findByEventDateBetweenAndInitiatorIdInAndCategoryIdIn(LocalDateTime rangeStart,
                      LocalDateTime rangeEnd, Iterable userIds, Iterable catIds, Pageable page);

    List<Event> findByIdIn(Iterable ids);

    Event getByIdAndState(long id, State state);

    Page<Event> findByEventDateBetweenAndCategoryIdInAndPaidAndAnnotationContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
            LocalDateTime rangeStart, LocalDateTime rangeEnd, Iterable categories, boolean paid, String annotation, String description, Pageable page);
}
