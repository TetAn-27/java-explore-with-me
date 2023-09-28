package ru.practicum.comments.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.CommentDtoForGet;

import java.util.List;
import java.util.Optional;

public class CommentServiceImpl implements CommentService {
    @Override
    public Optional<CommentDtoForGet> getEventForPublic(long comId) {
        return Optional.empty();
    }

    @Override
    public List<CommentDtoForGet> getAllCommentsEvent(long eventId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Optional<CommentDtoForGet> postComment(long userId, long eventId, CommentDto commentDto) {
        return Optional.empty();
    }

    @Override
    public Optional<CommentDtoForGet> updateComment(long userId, long eventId, CommentDto commentDto) {
        return Optional.empty();
    }
}
