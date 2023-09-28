package ru.practicum.comments.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.CommentDtoForGet;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<CommentDtoForGet> getEventForPublic(long comId);

    List<CommentDtoForGet> getAllCommentsEvent(long eventId, PageRequest pageRequest);

    Optional<CommentDtoForGet> postComment(long userId, long eventId, CommentDto commentDto);

    Optional<CommentDtoForGet> updateComment(long userId, long eventId, CommentDto commentDto);
}
