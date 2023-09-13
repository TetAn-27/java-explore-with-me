package ru.practicum;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<EndpointHit, Long> {

    EndpointHit save(EndpointHit endpointHit);
    List<EndpointHit.EndpointHitForGet> findByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, Iterable uri);
    List<EndpointHit.EndpointHitForGet> findDistinctByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, Iterable uri);
    List<EndpointHit.EndpointHitForGet> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<EndpointHit.EndpointHitForGet> findDistinctByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
