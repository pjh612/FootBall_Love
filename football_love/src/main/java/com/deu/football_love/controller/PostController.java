package com.deu.football_love.controller;

import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.post.*;
import com.deu.football_love.dto.post.like.LikePostResponse;
import com.deu.football_love.dto.team.QueryTeamMemberDto;
import com.deu.football_love.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PostController {

  private final PostService postService;
  private final TeamService teamService;

  /**
   * 게시글 생성
   */
  @PostMapping("/board/post")
  public ResponseEntity writePost(WritePostRequest writePostRequest, @AuthenticationPrincipal LoginInfo loginInfo) {
    TeamMemberType teamMemberType = teamService.authorityCheck(writePostRequest.getTeamId(), loginInfo.getNumber());
    if (teamMemberType == TeamMemberType.NONE) {
      throw new NotTeamMemberException();
    }
    WritePostResponse writePostResponse = postService.writePost(writePostRequest, loginInfo.getNumber());
    return new ResponseEntity(writePostResponse, HttpStatus.OK);
  }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId,@Valid @RequestBody DeletePostRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
        QueryPostDto findPost = postService.findPost(postId);

        if (findPost == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (loginInfo.getNumber() != findPost.getAuthorNumber())
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        DeletePostResponse response = postService.deletePost(findPost.getId());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/post/{postId}")
    public ResponseEntity modifyPost(@PathVariable Long postId,@Valid @RequestBody UpdatePostRequest request, @AuthenticationPrincipal  LoginInfo loginInfo) {
        QueryPostDto findPost = postService.findPost(postId);

        if (findPost == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (loginInfo.getNumber() != findPost.getAuthorNumber())
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        postService.modifyPost(postId, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity findPost(@PathVariable Long postId) {
        QueryPostDto post = postService.findPost(postId);
        if (post == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity(post, HttpStatus.OK);
    }

    @GetMapping("/board/{boardId}/post")
    public ResponseEntity<Page<QueryPostDto>> getPostList(@PathVariable Long boardId, Pageable pageable) {
        Page<QueryPostDto> result = postService.findAllPostsByBoardId(boardId, pageable);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @PostMapping("/post/{postId}/like")
    @ApiOperation("좋아요")
    public ResponseEntity likePost(@PathVariable Long postId, @AuthenticationPrincipal LoginInfo loginInfo) {
        LikePostResponse likePostResponse = postService.likePost(postId, loginInfo.getNumber());
        return new ResponseEntity(likePostResponse, HttpStatus.OK);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity handleAccessDeniedException(final IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
