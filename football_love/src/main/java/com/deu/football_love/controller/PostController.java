package com.deu.football_love.controller;

import java.io.IOException;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.post.DeletePostResponse;
import com.deu.football_love.dto.post.QueryPostDto;
import com.deu.football_love.dto.post.UpdatePostRequest;
import com.deu.football_love.dto.post.WritePostRequest;
import com.deu.football_love.dto.post.WritePostResponse;
import com.deu.football_love.dto.post.WriteTeamPostRequest;
import com.deu.football_love.dto.post.like.LikePostResponse;
import com.deu.football_love.exception.CustomException;
import com.deu.football_love.exception.NotTeamMemberException;
import com.deu.football_love.exception.error_code.ErrorCode;
import com.deu.football_love.service.PostService;
import com.deu.football_love.service.TeamService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PostController {

  private final PostService postService;
  private final TeamService teamService;

  /**
   * 팀게시판 게시글 생성
   */
  @PostMapping("/board/team/post")
  public ResponseEntity<WritePostResponse> writeTeamPost(WriteTeamPostRequest writePostRequest, @AuthenticationPrincipal LoginInfo loginInfo) {
    TeamMemberType teamMemberType = teamService.authorityCheck(writePostRequest.getTeamId(), loginInfo.getNumber());
    if (teamMemberType == TeamMemberType.NONE) {
      throw new NotTeamMemberException();
    }
    WritePostResponse writePostResponse = postService.writeTeamPost(writePostRequest, loginInfo.getNumber());
    return new ResponseEntity<WritePostResponse>(writePostResponse, HttpStatus.OK);
  }

  /**
   * 공용 게시글 생성
   */
  @PostMapping("/board/post")
  public ResponseEntity<WritePostResponse> writePost(WritePostRequest writePostRequest, @AuthenticationPrincipal LoginInfo loginInfo)
      throws IOException {
    WritePostResponse writePostResponse = postService.writePost(writePostRequest, loginInfo.getNumber());
    return new ResponseEntity<WritePostResponse>(writePostResponse, HttpStatus.OK);
  }

  /**
   * 게시글 삭제
   */
  @DeleteMapping("/post/{postId}")
  public ResponseEntity<DeletePostResponse> deletePost(@PathVariable Long postId, @AuthenticationPrincipal LoginInfo loginInfo) {
    QueryPostDto findPost = postService.findPost(postId);
    if (findPost == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_DATA);
    }
    if (loginInfo.getNumber() != findPost.getAuthorNumber()) {
      throw new CustomException(ErrorCode.NOT_POST_AUTHOR);
    }
    DeletePostResponse response = postService.deletePost(findPost.getId());
    return new ResponseEntity(response, HttpStatus.OK);
  }

  /**
   * 게시글 수정
   */
  @PutMapping("/post/{postId}")
  public ResponseEntity<QueryPostDto> modifyPost(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest request,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    QueryPostDto findPost = postService.findPost(postId);
    if (findPost == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_DATA);
    }
    if (loginInfo.getNumber() != findPost.getAuthorNumber()) {
      throw new CustomException(ErrorCode.NOT_POST_AUTHOR);
    }
    postService.modifyPost(postId, request);
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("/post/{postId}")
  public ResponseEntity<QueryPostDto> findPost(@PathVariable Long postId) {
    QueryPostDto post = postService.findPost(postId);
    return new ResponseEntity(post, HttpStatus.OK);
  }

  @GetMapping("/board/{boardId}/post")
  public ResponseEntity<Page<QueryPostDto>> getPostList(@PathVariable Long boardId, Pageable pageable) {
    Page<QueryPostDto> result = postService.findAllPostsByBoardId(boardId, pageable);
    return new ResponseEntity(result, HttpStatus.OK);
  }

  @PostMapping("/post/{postId}/like")
  @ApiOperation("좋아요")
  public ResponseEntity<LikePostResponse> likePost(@PathVariable Long postId, @AuthenticationPrincipal LoginInfo loginInfo) {
    LikePostResponse likePostResponse = postService.likePost(postId, loginInfo.getNumber());
    return new ResponseEntity(likePostResponse, HttpStatus.OK);
  }


}
