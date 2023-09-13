package ru.practicum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StateService {

    Optional<EndpointHit> addHit(EndpointHit endpointHit);
    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
