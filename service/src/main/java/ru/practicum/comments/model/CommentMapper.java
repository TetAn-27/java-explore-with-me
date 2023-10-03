package ru.practicum.comments.model;

import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.CommentDtoForGet;
import ru.practicum.events.model.Event;
import ru.practicum.users.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static CommentDtoForGet toCommentDtoForGet(Comment comment) {
        return new CommentDtoForGet(
                comment.getId(),
                comment.getText(),
                comment.getEvent().getId(),
                comment.getAuthor().getId(),
                comment.getAuthor().getName(),
                comment.getCreatedDate(),
                comment.isInitiator(),
                comment.isUpdate()
        );
    }

    public static Comment toComment(CommentDto commentDto, Event event, User author) {
        return new Comment(
                0,
                commentDto.getText(),
                event,
                author,
                LocalDateTime.now(),
                author.getId() == event.getInitiator().getId(),
                false
        );
    }

    public static List<CommentDtoForGet> toListCommentDto(List<Comment> commentList) {
        List<CommentDtoForGet> commentDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentDtoList.add(toCommentDtoForGet(comment));
        }
        return commentDtoList;
    }
}
