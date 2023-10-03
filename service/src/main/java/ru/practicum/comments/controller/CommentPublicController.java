package ru.practicum.comments.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDtoForGet;
import ru.practicum.comments.service.CommentPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/comments")
public class CommentPublicController {

    private final CommentPublicService commentPublicService;

    public CommentPublicController(CommentPublicService commentPublicService) {
        this.commentPublicService = commentPublicService;
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDtoForGet> getEventForPublic(@PathVariable(value = "commentId") Long comId) {
        return ResponseEntity.of(commentPublicService.getCommentForPublic(comId));
    }

    @GetMapping("/events/{eventId}")
    public List<CommentDtoForGet> getAllCommentsEvent(@PathVariable(value = "eventId") Long eventId,
                                                      @PositiveOrZero @RequestParam(value = "from", defaultValue = "0",
                                                        required = false) int page,
                                                      @Positive @RequestParam(value = "size", defaultValue = "10",
                                                        required = false) int size) {
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.ASC, "id");
        return commentPublicService.getAllCommentsEvent(eventId, pageRequest);
    }
}
