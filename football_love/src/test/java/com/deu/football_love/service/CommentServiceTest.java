package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Comment;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.AddBoardResponse;
import com.deu.football_love.dto.board.BoardDto;
import com.deu.football_love.dto.comment.*;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.post.WritePostRequest;
import com.deu.football_love.dto.post.WritePostResponse;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.repository.BoardRepository;
import com.deu.football_love.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    PostService postService;

    @Autowired
    TeamService teamService;

    @Autowired
    CommentService commentService;

    @Autowired
    MemberService memberService;

    Long postId = 0L;
    Long writerNumber = 0L;
    @BeforeEach
    void before(){
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA","1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());
        WritePostRequest writePostRequest = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);
        WritePostResponse writePostResponse = postService.writePost(writePostRequest);
        postId = writePostResponse.getPostId();
        writerNumber = memberJoinResponse.getNumber();
    }

    @Test
    void addComment() {
        AddCommentRequest request = new AddCommentRequest(postId, writerNumber, "댓글 테스트 입니다.");
        AddCommentResponse addCommentResponse = commentService.addComment(request);

        Assertions.assertTrue(addCommentResponse.getSuccess());
        Assertions.assertNotNull(addCommentResponse.getCommentId());

    }

    @Test
    void findCommentByCommentId() {
        String comment = "댓글 테스트 입니다.";
        AddCommentRequest request = new AddCommentRequest(postId, writerNumber, comment);
        AddCommentResponse addCommentResponse = commentService.addComment(request);

        QueryCommentDto findComment = commentService.findCommentByCommentId(addCommentResponse.getCommentId());

        Assertions.assertEquals(comment,findComment.getComment());
        Assertions.assertEquals(writerNumber,findComment.getWriterNumber());
        Assertions.assertEquals(postId,findComment.getPostId());
        Assertions.assertEquals(addCommentResponse.getCommentId(),findComment.getCommentId());
    }

    @Test
    void findCommentsByPostId() {
        String comment = "댓글 테스트 입니다.";
        String comment2 = "댓글 테스트2 입니다.";
        AddCommentRequest request = new AddCommentRequest(postId, writerNumber, comment);
        AddCommentRequest request2 = new AddCommentRequest(postId, writerNumber, comment2);
        AddCommentResponse addCommentResponse = commentService.addComment(request);
        AddCommentResponse addCommentResponse2 = commentService.addComment(request2);

        List<QueryCommentDto> findComments = commentService.findCommentsByPostId(postId);

        Assertions.assertEquals(2, findComments.size());
        Assertions.assertEquals(comment,findComments.get(0).getComment());
        Assertions.assertEquals(writerNumber,findComments.get(0).getWriterNumber());
        Assertions.assertEquals(postId,findComments.get(0).getPostId());
        Assertions.assertEquals(addCommentResponse.getCommentId(),findComments.get(0).getCommentId());

        Assertions.assertEquals(comment2,findComments.get(1).getComment());
        Assertions.assertEquals(writerNumber,findComments.get(1).getWriterNumber());
        Assertions.assertEquals(postId,findComments.get(1).getPostId());
        Assertions.assertEquals(addCommentResponse2.getCommentId(),findComments.get(1).getCommentId());
    }

    @Test
    void updateCommentByCommentId() {
        String comment = "댓글 테스트 입니다.";
        String AfterUpdateComment = "댓글 변경 테스트 입니다.";
        AddCommentRequest request = new AddCommentRequest(postId, writerNumber, comment);
        AddCommentResponse addCommentResponse = commentService.addComment(request);

        commentService.updateCommentByCommentId(new UpdateCommentRequest(addCommentResponse.getCommentId(), AfterUpdateComment));
        QueryCommentDto findComment = commentService.findCommentByCommentId(addCommentResponse.getCommentId());
        Assertions.assertEquals(AfterUpdateComment,findComment.getComment());
        Assertions.assertEquals(writerNumber,findComment.getWriterNumber());
        Assertions.assertEquals(postId,findComment.getPostId());
        Assertions.assertEquals(addCommentResponse.getCommentId(),findComment.getCommentId());
    }

    @Test
    void deleteCommentByCommentId() {
        String comment = "댓글 테스트 입니다.";
        AddCommentRequest request = new AddCommentRequest(postId, writerNumber, comment);
        AddCommentResponse addCommentResponse = commentService.addComment(request);

        commentService.deleteCommentByCommentId(addCommentResponse.getCommentId());
        Assertions.assertThrows(IllegalArgumentException.class
                , ()->commentService.findCommentByCommentId(addCommentResponse.getCommentId()));
    }
}