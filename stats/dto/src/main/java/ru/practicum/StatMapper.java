package ru.practicum;

import java.util.List;

public class StatMapper {

    public static ViewStats toViewStats(EndpointHit endpointHit, int hits) {
        return new ViewStats(
                endpointHit.getApp(),
                endpointHit.getUri(),
                hits
        );
    }
}
