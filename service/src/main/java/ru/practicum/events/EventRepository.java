package ru.practicum.events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAllByInitiatorId(long id, Pageable page);

    Event save(Event event);

    Event getById(long Id);

    @Query("SELECT e from Event e " +
            "WHERE (:users is null or e.initiator.id in :users) " +
            "AND (:states is null or e.state in :states) " +
            "AND (:categories is null or e.category.id in :categories) " +
            "AND e.eventDate > :rangeStart " +
            "AND e.eventDate < :rangeEnd")
    Page<Event> getAllEventsInfo(List<Long> users, List<State> states, List<Long> categories,
                                   LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable page);

    List<Event> findByIdIn(Iterable ids);

    Event getByIdAndState(long id, State state);

    @Query("select e from Event e " +
            "where e.state = 'PUBLISHED' " +
            "and (:text is null or upper(e.annotation) like upper(concat('%', :text, '%')) or upper(e.description) " +
            "like upper(concat('%', :text, '%')))" +
            "and (:categories is null or e.category.id in :categories) " +
            "and (:paid is null or e.paid = :paid) " +
            "and e.eventDate > :rangeStart " +
            "and e.eventDate < :rangeEnd " +
            "and (:onlyAvailable is null or e.confirmedRequests < e.participantLimit or e.participantLimit = 0)")
    Page<Event> getPublicEventsInfo(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable page);

    Event findFirstByCategoryId(long id);
}
