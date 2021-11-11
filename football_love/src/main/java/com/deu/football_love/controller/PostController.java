package com.deu.football_love.controller;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Post;
import com.deu.football_love.dto.UpdatePostRequest;
import com.deu.football_love.dto.WritePostRequest;
import com.deu.football_love.dto.WritePostResponse;
import com.deu.football_love.service.BoardService;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.PostService;
import com.deu.football_love.service.TeamService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController("/")
@RequiredArgsConstructor
public class PostController {

    private final MemberService memberService;
    private final PostService postService;
    private final TeamService teamService;
    private final BoardService boardService;

    /**
     * 게시글 생성
     */
    @PostMapping("/team/{teamName}/board/{boardId}/post")
    public ResponseEntity writePost(@PathVariable String teamName, @PathVariable Long boardId
                                    , WritePostRequest writePostRequest, HttpSession session)
    {
        Board board = new Board();// board 조회
        Member sessionMember = memberService.findMember(((Member) session.getAttribute("memberInfo")).getId());
        if (sessionMember == null || board == null || teamService.findTeamMember(teamName, sessionMember.getId()).size() == 0)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        Post newPost = new Post(sessionMember, board, writePostRequest.getCreateDate()
                , writePostRequest.getModifyDate(), writePostRequest.getTitle(), writePostRequest.getContent());
        WritePostResponse writePostResponse = postService.writePost(newPost);
        return new ResponseEntity(writePostResponse, HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId, WritePostRequest writePostRequest, HttpSession session)
    {
        Member sessionMember = memberService.findMember(((Member) session.getAttribute("memberInfo")).getId());
        Post findPost = postService.findPost(postId);

        if (findPost == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (sessionMember == null || sessionMember.getId() != findPost.getAuthor().getId())
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        postService.deletePost(findPost);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/post/{postId}")
    public ResponseEntity modifyPost(@PathVariable Long postId, UpdatePostRequest request, HttpSession session)
    {
        Member sessionMember = memberService.findMember(((Member) session.getAttribute("memberInfo")).getId());
        Post findPost = postService.findPost(postId);

        if (findPost == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (sessionMember == null || sessionMember.getId() != findPost.getAuthor().getId())
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        postService.modifyPost(findPost, request);
        return new ResponseEntity(HttpStatus.OK);
    }

}
