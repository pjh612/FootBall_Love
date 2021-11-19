package com.deu.football_love.controller;

import com.deu.football_love.controller.consts.SessionConst;
import com.deu.football_love.domain.Board;
import com.deu.football_love.dto.*;
import com.deu.football_love.service.BoardService;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.PostService;
import com.deu.football_love.service.TeamService;
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
    public ResponseEntity writePost(@PathVariable Long teamId, @PathVariable Long boardId
                                    , WritePostRequest writePostRequest, HttpSession session)
    {
        Board board = new Board();// board 조회
        MemberDto sessionMember = memberService.findMember(((MemberDto) session.getAttribute("memberInfo")).getId());
        if (sessionMember == null || board == null || teamService.findTeamMember(teamName, sessionMember.getId()).size() == 0)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        WritePostResponse writePostResponse = postService.writePost(writePostRequest);
        return new ResponseEntity(writePostResponse, HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId, DeletePostRequest request, HttpSession session)
    {
        MemberDto sessionMember = memberService.findMember(((MemberDto) session.getAttribute("memberInfo")).getId());
        PostDto findPost = postService.findPost(postId);

        if (findPost == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (sessionMember == null || sessionMember.getId() != findPost.getAuthorId())
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        DeletePostResponse response = postService.deletePost(findPost.getId());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/post/{postId}")
    public ResponseEntity modifyPost(@PathVariable Long postId, UpdatePostRequest request, HttpSession session)
    {
        MemberDto sessionMember = memberService.findMember(((MemberDto) session.getAttribute("memberInfo")).getId());
        PostDto findPost = postService.findPost(postId);

        if (findPost == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (sessionMember == null || sessionMember.getId() != findPost.getAuthorId())
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        postService.modifyPost(postId, request);
        return new ResponseEntity(HttpStatus.OK);
    }

}
