package ru.practicum.comments.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.comments.dto.CommentDtoForGet;

import java.util.List;
import java.util.Optional;

public interface CommentPublicService {

    Optional<CommentDtoForGet> getEventForPublic(long comId);

    List<CommentDtoForGet> getAllCommentsEvent(long eventId, PageRequest pageRequest);
}
