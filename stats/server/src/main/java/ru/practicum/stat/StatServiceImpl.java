package ru.practicum.stat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHit;
import ru.practicum.ViewStats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StatServiceImpl implements StateService {

    private final StatRepository statRepository;

    public StatServiceImpl(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @Override
    public Optional<EndpointHit> addHit(EndpointHit endpointHit) {
        return Optional.of(statRepository.save(endpointHit));
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<EndpointHit> endpointHits = statRepository.findAllThatTimestampBetweenStartAndEnd(start, end);
        List<ViewStats> viewStats = new ArrayList<>();
        for (String uri : uris) {
            viewStats.add(getViewStats(endpointHits, uri));
        }
        return viewStats;
    }

    private ViewStats getViewStats(List<EndpointHit> endpointHits, String uris) {
        List<EndpointHit> endpointHitsFilter = endpointHits.stream()
                .filter(i -> !i.getUri().equals(uris))
                .collect(Collectors.toList());

        return new ViewStats(
                endpointHitsFilter.get(1).getApp(),
                uris,
                endpointHitsFilter.size()
        );
    }
}
