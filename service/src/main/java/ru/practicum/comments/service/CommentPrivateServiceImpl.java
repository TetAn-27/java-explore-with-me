package ru.practicum.comments.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.practicum.comments.CommentRepository;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.CommentDtoForGet;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.model.CommentMapper;
import ru.practicum.events.EventRepository;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.State;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.users.UserRepository;
import ru.practicum.users.model.User;

import java.util.Optional;

@Slf4j
@Service
public class CommentPrivateServiceImpl implements CommentPrivateService {

    private static final String CONFLICT_USER_NOT_CREATOR = "Пользователь не является создателем комментария." +
            "Изменение комментария невозможно";
    private static final String CONFLICT_COM_NOT_APPLY_EVENT = "Данный комментарий не относится к этому событию." +
            "Изменение комментария невозможно";
    private static final String NOT_FOUND_COM = "Комментария с таким id не найдено";

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public CommentPrivateServiceImpl(CommentRepository commentRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Optional<CommentDtoForGet> postComment(long userId, long eventId, CommentDto commentDto) {
        User author = getAuthor(userId);
        Event event = getEvent(eventId);
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Событие не опубликовано, оставить комментарий невозможно");
        }
        Comment comment = CommentMapper.toComment(commentDto, event, author);
        log.debug("Comment с от пользователя {} был создан", author.getName());
        return Optional.of(CommentMapper.toCommentDtoForGet(commentRepository.save(comment)));
    }

    @Override
    public Optional<CommentDtoForGet> updateComment(long userId, long eventId, long commentId, CommentDto commentDto) {
        Comment comment = getComment(commentId);
        if (userId != comment.getAuthor().getId()) {
            throw new ConflictException(CONFLICT_USER_NOT_CREATOR);
        }
        if (eventId != comment.getEvent().getId()) {
            throw new ConflictException(CONFLICT_COM_NOT_APPLY_EVENT);
        }
        Comment commentNew = getUpdateComment(comment, commentDto);
        log.debug("Comment с id {} был обновлен", commentNew.getId());
        return Optional.of(CommentMapper.toCommentDtoForGet(commentRepository.save(commentNew)));
    }

    @Override
    public void deleteComment(long userId, long eventId, long commentId) {
        Comment comment = getComment(commentId);
        if (userId != comment.getAuthor().getId()) {
            throw new ConflictException(CONFLICT_USER_NOT_CREATOR);
        }
        if (eventId != comment.getEvent().getId()) {
            throw new ConflictException(CONFLICT_COM_NOT_APPLY_EVENT);
        }
        try {
            commentRepository.deleteById(commentId);
        } catch (DataAccessException ex) {
            log.error("Комментария с Id {} не найдено", commentId);
            throw new NotFoundException(NOT_FOUND_COM);
        }
    }

    private User getAuthor(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден"));
    }

    private Event getEvent(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Событие с таким id не найдено"));
    }

    private Comment getComment(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_COM));
    }

    private Comment getUpdateComment(Comment comment, CommentDto commentDto) {
        comment.setText(commentDto.getText());
        comment.setUpdate(true);
        return comment;
    }
}
