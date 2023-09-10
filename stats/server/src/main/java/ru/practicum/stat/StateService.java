package ru.practicum.stat;

import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;

import java.util.List;
import java.util.Optional;

public interface StateService {

    Optional<EndpointHit> addHit(EndpointHit endpointHit);
    List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique);
}
