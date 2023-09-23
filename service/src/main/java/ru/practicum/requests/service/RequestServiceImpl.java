package ru.practicum.requests.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.events.EventRepository;
import ru.practicum.events.model.Event;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.requests.RequestRepository;
import ru.practicum.requests.model.*;
import ru.practicum.users.UserRepository;
import ru.practicum.users.model.User;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public RequestServiceImpl(RequestRepository requestRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }
    @Override
    public List<ParticipationRequestDto> getRequestCurrentUser(long userId, long eventId) {
        try {
            return RequestMapper.toListRequestDto(requestRepository.findByEventId(eventId));
        } catch (EntityNotFoundException ex) {
            log.debug("Запросы на участие в событие с id {} не найдены", eventId);
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<EventRequestStatus> updateRequestCurrentUser(long userId, long eventId, EventRequestStatusUpdate bodyUpdate) {
        Event event = eventRepository.getById(eventId);
        int limit = event.getParticipantLimit();
        boolean requirement = (limit == 0) || (!event.isRequestModeration());
        List<ParticipationRequest> requests = requestRepository.findByIdIn(bodyUpdate.getRequestIds());
        if (requirement) {
            return Optional.of(new EventRequestStatus(
                    RequestMapper.toListRequestDto(confirmedRequest(requests)),
                    RequestMapper.toListRequestDto(getRejectedRequest(requests))
            ));
        }
        if (bodyUpdate.getStatus().equals(Status.CONFIRMED)) {
            requests = setStatusRequestWithLimit(requests, limit);
            return Optional.of(new EventRequestStatus(
                    RequestMapper.toListRequestDto(getConfirmedRequest(requests)),
                    RequestMapper.toListRequestDto(getRejectedRequest(requests))
            ));
        } else {
            requests = setStatusRequestWithLimit(requests, 0);
            return Optional.of(new EventRequestStatus(
                    RequestMapper.toListRequestDto(getConfirmedRequest(requests)),
                    RequestMapper.toListRequestDto(getRejectedRequest(requests))
            ));
        }
    }

    @Override
    public List<ParticipationRequestDto> getRequestOtherUser(long userId) {
        try {
            return RequestMapper.toListRequestDto(requestRepository.findByRequesterId(userId));
        } catch (EntityNotFoundException ex) {
            log.error("Запросы пользователья с id {} не найдены", userId);
            throw new NotFoundException("Запросы пользователья  с таким id не были найдены");
        }
    }

    @Override
    public Optional<ParticipationRequestDto> addRequest(long userId, long eventId) {
        Event event = eventRepository.getById(eventId);
        User requester = userRepository.getById(userId);
        ParticipationRequest request = new ParticipationRequest(
                0,
                LocalDateTime.now(),
                event,
                requester,
                Status.PENDING
        );
        return Optional.of(RequestMapper.toRequestDto(requestRepository.save(request)));
    }

    @Override
    public Optional<ParticipationRequestDto> cancelRequest(long userId, long requestId) {
        ParticipationRequest request = requestRepository.getById(requestId);
        if (request.getRequester().getId() != userId) {
            throw new ConflictException("Вы не можете отменить чужой запрос");
        }
        request.setStatus(Status.REJECTED);
        return Optional.of(RequestMapper.toRequestDto(requestRepository.save(request)));
    }

    private List<ParticipationRequest> confirmedRequest(List<ParticipationRequest> requests) {
        List<ParticipationRequest> confirmedRequestList = new ArrayList<>();
        for (ParticipationRequest request : requests) {
            if (request.getStatus().equals(Status.PENDING)) {
                request.setStatus(Status.CONFIRMED);
                confirmedRequestList.add(request);
            }
        }
        return confirmedRequestList;
    }

    private List<ParticipationRequest> getRejectedRequest(List<ParticipationRequest> requests) {
        List<ParticipationRequest> confirmedRequestList = new ArrayList<>();
        for (ParticipationRequest request : requests) {
            if (request.getStatus().equals(Status.REJECTED)) {
                confirmedRequestList.add(request);
            }
        }
        return confirmedRequestList;
    }

    private List<ParticipationRequest> getConfirmedRequest(List<ParticipationRequest> requests) {
        List<ParticipationRequest> confirmedRequestList = new ArrayList<>();
        for (ParticipationRequest request : requests) {
            if (request.getStatus().equals(Status.CONFIRMED)) {
                confirmedRequestList.add(request);
            }
        }
        return confirmedRequestList;
    }

    private List<ParticipationRequest> setStatusRequestWithLimit(List<ParticipationRequest> requests, int limit) {
        for (ParticipationRequest request : requests) {
            if (request.getStatus().equals(Status.PENDING)) {
                if (limit > 0) {
                    request.setStatus(Status.CONFIRMED);
                    limit--;
                } else {
                    request.setStatus(Status.REJECTED);
                }
            }
        }
        return requests;
    }
}
