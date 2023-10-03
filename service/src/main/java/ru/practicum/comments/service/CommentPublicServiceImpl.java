package ru.practicum.comments.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.comments.CommentRepository;
import ru.practicum.comments.dto.CommentDtoForGet;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.model.CommentMapper;
import ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentPublicServiceImpl implements CommentPublicService {

    private final CommentRepository commentRepository;

    public CommentPublicServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<CommentDtoForGet> getCommentForPublic(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментария с таким id не найдено"));
        log.debug("Comment: {}", comment);
        return Optional.of(CommentMapper.toCommentDtoForGet(comment));
    }

    @Override
    public List<CommentDtoForGet> getAllCommentsEvent(long eventId, PageRequest pageRequest) {
        Pageable page = pageRequest;
        List<CommentDtoForGet> comments = CommentMapper.toListCommentDto(commentRepository.findAllByEventId(eventId, page));
        log.debug("По данному запросу нашлось {} комментариев", comments.size());
        return comments;
    }
}
