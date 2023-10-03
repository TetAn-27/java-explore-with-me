package ru.practicum.comments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.CommentDtoForGet;
import ru.practicum.comments.service.CommentPrivateService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users/{userId}/events/{eventId}/comments")
public class CommentPrivateController {

    private final CommentPrivateService commentPrivateService;

    public CommentPrivateController(CommentPrivateService commentPrivateService) {
        this.commentPrivateService = commentPrivateService;
    }

    @PostMapping()
    public ResponseEntity<CommentDtoForGet> postComment(@PathVariable(value = "userId") Long userId,
                                                        @PathVariable(value = "eventId") Long eventId,
                                                        @RequestBody @Valid CommentDto commentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentPrivateService.postComment(userId, eventId, commentDto).get());
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDtoForGet> updateComment(@PathVariable(value = "userId") Long userId,
                                                          @PathVariable(value = "eventId") Long eventId,
                                                          @PathVariable(value = "commentId") Long commentId,
                                                          @RequestBody @Valid CommentDto commentDto) {
        return ResponseEntity.of(commentPrivateService.updateComment(userId, eventId, commentId, commentDto));
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable(value = "userId") Long userId,
                              @PathVariable(value = "eventId") Long eventId,
                              @PathVariable("commentId") Long commentId) {
        commentPrivateService.deleteComment(userId, eventId, commentId);
    }
}
