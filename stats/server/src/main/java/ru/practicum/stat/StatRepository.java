package ru.practicum.stat;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.dto.EndpointHit;

import java.util.List;

public interface StatRepository extends JpaRepository<EndpointHit, Long> {

    EndpointHit save(EndpointHit endpointHit);
    List<EndpointHit.EndpointHitForGet> findByTimestampBetweenAndUriIn(String start, String end, Iterable uri);
    List<EndpointHit.EndpointHitForGet> findDistinctByTimestampBetweenAndUriIn(String start, String end, Iterable uri);
    List<EndpointHit.EndpointHitForGet> findByTimestampBetween(String start, String end);
    List<EndpointHit.EndpointHitForGet> findDistinctByTimestampBetween(String start, String end);
}
