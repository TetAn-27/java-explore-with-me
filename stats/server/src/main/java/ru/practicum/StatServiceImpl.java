package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.exception.BadRequestException;

import java.time.LocalDateTime;
import java.util.*;
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
        List<EndpointHit.EndpointHitForGet> endpointHits;
        if (start.isAfter(end)) {
            throw new BadRequestException("Начало события не может быть после конца события");
        }
        if (uris==null) {
            if (unique) {
                endpointHits = statRepository.findDistinctByTimestampBetween(start, end);
                return getViewStatsFromMap(groupByUri(endpointHits));
            }
            endpointHits = statRepository.findByTimestampBetween(start, end);
            return getViewStatsFromMap(groupByUri(endpointHits));
        }
        if (unique) {
            endpointHits = statRepository.findDistinctByTimestampBetweenAndUriIn(start, end, uris);
            return getViewStatsFromMap(groupByUri(endpointHits));
        }
        endpointHits = statRepository.findByTimestampBetweenAndUriIn(start, end, uris);
        return getViewStatsFromMap(groupByUri(endpointHits));
    }

    private Map<String, List<EndpointHit.EndpointHitForGet>> groupByUri(List<EndpointHit.EndpointHitForGet> endpointHits) {
        return endpointHits.stream()
                .collect(Collectors.groupingBy(EndpointHit.EndpointHitForGet::getUri));
    }

    private List<ViewStats> getViewStatsFromMap(Map<String, List<EndpointHit.EndpointHitForGet>> endpointHitsMap) {
        List<ViewStats> viewStatsList = new ArrayList<>();
        for (String uri : endpointHitsMap.keySet()) {
            List<EndpointHit.EndpointHitForGet> endpointHitList = endpointHitsMap.get(uri);
            viewStatsList.add(new ViewStats(
                    endpointHitList.get(0).getApp(),
                    uri,
                    endpointHitList.size()));
        }
        return viewStatsList.stream()
                .sorted(Comparator.comparingLong(ViewStats::getHits).reversed())
                .collect(Collectors.toList());
    }
}
