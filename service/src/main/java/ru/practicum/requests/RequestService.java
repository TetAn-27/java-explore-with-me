package ru.practicum.requests;

import ru.practicum.requests.model.ParticipationRequestDto;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    List<ParticipationRequestDto> getRequestCurrentUser(long userId, long eventId);

    Optional<ParticipationRequestDto> updateRequestCurrentUser(long userId, long eventId, ParticipationRequestDto bodyDto);

    List<ParticipationRequestDto> getRequestOtherUser(long userId);

    Optional<ParticipationRequestDto> addRequest(long userId, long eventId);

    Optional<ParticipationRequestDto> cancelRequest(long userId, long requestId);
}
