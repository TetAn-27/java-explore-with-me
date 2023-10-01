package ru.practicum.comments;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comments.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment save(Comment comment);

    Comment getById(long id);

    List<Comment> findAllByEventId(long eventId, Pageable page);

    List<Comment> findByEventId(long eventId);
}
