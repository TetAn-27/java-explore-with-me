package ru.practicum.requests.model;

import ru.practicum.events.model.Event;
import ru.practicum.users.model.User;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {

    public static ParticipationRequestDto toRequestDto(ParticipationRequest request) {
        return new ParticipationRequestDto(
                request.getId(),
                request.getCreated(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }

    public static ParticipationRequest toRequest(ParticipationRequestDto requestDto, Event event, User requester) {
        return new ParticipationRequest(
                0,
                requestDto.getCreated(),
                event,
                requester,
                requestDto.getStatus()
        );
    }

    public static List<ParticipationRequestDto> toListRequestDto(List<ParticipationRequest> requestList) {
        List<ParticipationRequestDto> requestDtoList = new ArrayList<>();
        for (ParticipationRequest request : requestList) {
            requestDtoList.add(toRequestDto(request));
        }
        return requestDtoList;
    }
}
