package ru.practicum.comments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.CommentDtoForGet;
import ru.practicum.comments.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users")
public class CommentPrivateController {

    private final CommentService commentService;

    public CommentPrivateController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{userId}/events/{eventId}/comments")
    public ResponseEntity<CommentDtoForGet> postComment(@PathVariable(value = "userId") Long userId,
                                                        @PathVariable(value = "eventId") Long eventId,
                                                        @RequestBody @Valid CommentDto commentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.postComment(userId, eventId, commentDto).get());
    }

    @PatchMapping("/{userId}/events/{eventId}/comments")
    public ResponseEntity<CommentDtoForGet> updateComment(@PathVariable(value = "userId") Long userId,
                                                          @PathVariable(value = "eventId") Long eventId,
                                                          @RequestBody @Valid CommentDto commentDto) {
        return ResponseEntity.of(commentService.updateComment(userId, eventId, commentDto));
    }
}
