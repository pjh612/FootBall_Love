package com.deu.football_love.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.BoardDto;
import com.deu.football_love.dto.comment.AddCommentRequest;
import com.deu.football_love.dto.comment.AddCommentResponse;
import com.deu.football_love.dto.comment.DeleteCommentRequest;
import com.deu.football_love.dto.comment.UpdateCommentRequest;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.post.WritePostRequest;
import com.deu.football_love.dto.post.WritePostResponse;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.service.BoardService;
import com.deu.football_love.service.CommentService;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.PostService;
import com.deu.football_love.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentControllerTest {


  @Autowired
  private MemberService memberService;

  @Autowired
  private TeamService teamService;

  @Autowired
  private BoardService boardService;

  @Autowired
  private PostService postService;

  @Autowired
  private CommentService commentService;

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  MockMvc mvc;

  ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

  String writerId;
  Long writerNumber;
  Long boardId;
  Long teamId;
  Long postId;
  UserDetails loginUser;

  @BeforeEach
  public void before() {
    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "팀 A 소개");
    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    BoardDto findBoard = boardService.findById(boardService
        .add(new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId())).getBoardId());

    writerNumber = memberJoinResponse.getNumber();
    writerId = memberJoinResponse.getId();
    boardId = findBoard.getBoardId();
    teamId = teamA.getTeamId();
    WritePostRequest writePostRequest =
        new WritePostRequest(writerNumber, boardId, teamId, "title1", "hi", null);
    WritePostResponse writePostResponse = postService.writePost(writePostRequest);
    postId = writePostResponse.getPostId();

    loginUser = userDetailsService.loadUserByUsername(writerId);
  }

  @DisplayName("댓글 달기 테스트")
  @Test
  public void addComment() throws Exception {
    AddCommentRequest addCommentRequest =
        new AddCommentRequest(postId, writerNumber, "댓글 컨트롤러 테스트입니다.");
    mvc.perform(MockMvcRequestBuilders.post("/api/comment").with(user(loginUser))
        .contentType("application/json").content(mapper.writeValueAsString(addCommentRequest)))
        .andExpect(status().isOk()).andDo(print());
  }

  @DisplayName("댓글 삭제 테스트")
  @Test
  public void deleteComment() throws Exception {
    AddCommentRequest addCommentRequest =
        new AddCommentRequest(postId, writerNumber, "댓글 컨트롤러 테스트입니다.");
    AddCommentResponse addCommentResponse = commentService.addComment(addCommentRequest);

    DeleteCommentRequest deleteCommentRequest =
        new DeleteCommentRequest(addCommentResponse.getCommentId());
    mvc.perform(MockMvcRequestBuilders.delete("/api/comment").with(user(loginUser))
        .contentType("application/json").content(mapper.writeValueAsString(deleteCommentRequest)))
        .andExpect(status().isOk()).andDo(print());
  }

  @DisplayName("댓글 수정 테스트")
  @Test
  public void updateComment() throws Exception {
    AddCommentRequest addCommentRequest =
        new AddCommentRequest(postId, writerNumber, "댓글 컨트롤러 테스트입니다.");
    AddCommentResponse addCommentResponse = commentService.addComment(addCommentRequest);

    UpdateCommentRequest updateCommentRequest =
        new UpdateCommentRequest(addCommentResponse.getCommentId(), "댓글 수정 API 테스트 입니다.");
    mvc.perform(MockMvcRequestBuilders.patch("/api/comment").with(user(loginUser))
        .contentType("application/json").content(mapper.writeValueAsString(updateCommentRequest)))
        .andExpect(status().isOk()).andDo(print());
  }
}
