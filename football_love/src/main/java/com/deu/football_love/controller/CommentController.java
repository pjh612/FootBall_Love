package com.deu.football_love.controller;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.comment.AddCommentRequest;
import com.deu.football_love.dto.comment.DeleteCommentRequest;
import com.deu.football_love.dto.comment.QueryCommentDto;
import com.deu.football_love.dto.comment.UpdateCommentRequest;
import com.deu.football_love.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

  private final CommentService commentService;

  @ApiOperation("댓글 추가")
  @PostMapping("/comment")
  public ResponseEntity add(@Valid @RequestBody AddCommentRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
    if (loginInfo.getNumber() != request.getWriterNumber())
      return new ResponseEntity(HttpStatus.FORBIDDEN);

    if (commentService.addComment(request).getSuccess())
      return new ResponseEntity(HttpStatus.OK);
    else
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
  }

  @ApiOperation("댓글 삭제")
  @DeleteMapping("/comment")
  public ResponseEntity delete(@Valid @RequestBody DeleteCommentRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
    QueryCommentDto findComment = commentService.findCommentByCommentId(request.getCommentId());
    if (findComment.getWriterNumber() != loginInfo.getNumber())
      return new ResponseEntity(HttpStatus.FORBIDDEN);
    commentService.deleteCommentByCommentId(request.getCommentId());
    return new ResponseEntity(HttpStatus.OK);
  }

  @PatchMapping("/comment")
  public ResponseEntity update(@Valid @RequestBody UpdateCommentRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
    QueryCommentDto findComment = commentService.findCommentByCommentId(request.getCommentId());
    if (findComment.getWriterNumber() != loginInfo.getNumber())
      return new ResponseEntity(HttpStatus.FORBIDDEN);
    commentService.updateCommentByCommentId(request);
    return new ResponseEntity(HttpStatus.OK);
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity handleAccessDeniedException(final IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
