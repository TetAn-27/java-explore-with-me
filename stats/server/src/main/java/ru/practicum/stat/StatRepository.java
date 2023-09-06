package ru.practicum.stat;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.EndpointHit;
import ru.practicum.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<EndpointHit, Integer> {

    EndpointHit save(EndpointHit endpointHit);
    //List<EndpointHit> findAllThatTimestampBetweenStartAndEnd(LocalDateTime start, LocalDateTime end);
}
