package ru.practicum.events.model;

import lombok.ToString;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.categories.model.CategoryMapper;
import ru.practicum.users.model.User;
import ru.practicum.users.model.UserMapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventMapper {

    public static EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.isRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getEventDate(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.isPaid(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static Event toEvent(EventDto eventDto, Category category, User initiator, long views) {
        return new Event(
                0,
                eventDto.getAnnotation(),
                category,
                eventDto.getConfirmedRequests(),
                eventDto.getCreatedOn(),
                eventDto.getDescription(),
                eventDto.getEventDate(),
                initiator,
                eventDto.getLocation(),
                eventDto.isPaid(),
                eventDto.getParticipantLimit(),
                eventDto.getPublishedOn(),
                eventDto.isRequestModeration(),
                eventDto.getState(),
                eventDto.getTitle(),
                views
        );
    }

    public static List<EventShortDto> toListEventShortDto(List<Event> eventList) {
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        for (Event event : eventList) {
            eventShortDtoList.add(toEventShortDto(event));
        }
        return eventShortDtoList;
    }

    public static List<EventFullDto> toListEventFullDto(List<Event> eventList) {
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for (Event event : eventList) {
            eventFullDtoList.add(toEventFullDto(event));
        }
        return eventFullDtoList;
    }
}
