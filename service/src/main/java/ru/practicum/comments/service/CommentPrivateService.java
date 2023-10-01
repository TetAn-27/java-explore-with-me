package ru.practicum.comments.service;

import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.CommentDtoForGet;

import java.util.Optional;

public interface CommentPrivateService {

    Optional<CommentDtoForGet> postComment(long userId, long eventId, CommentDto commentDto);

    Optional<CommentDtoForGet> updateComment(long userId, long eventId, long commentId, CommentDto commentDto);

    void deleteComment(long userId, long eventId, long commentId);
}
