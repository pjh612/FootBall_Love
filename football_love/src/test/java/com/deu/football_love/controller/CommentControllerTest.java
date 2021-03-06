package com.deu.football_love.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.deu.football_love.dto.auth.LoginInfo;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.Teamboard.AddTeamBoardRequest;
import com.deu.football_love.dto.Teamboard.TeamBoardDto;
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
import com.deu.football_love.service.CommentService;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamBoardService;
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
  private TeamBoardService boardService;

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
  LoginInfo loginUser;

  @BeforeEach
  public void before() {
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "??? A ??????");
    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    TeamBoardDto findBoard =
        boardService.findById(boardService.add(new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId())).getBoardId());

    writerNumber = memberJoinResponse.getNumber();
    writerId = memberJoinResponse.getId();
    boardId = findBoard.getBoardId();
    teamId = teamA.getTeamId();
    WritePostRequest writePostRequest = new WritePostRequest(boardId, teamId, "title1", "hi", null);
    WritePostResponse writePostResponse = postService.writePost(writePostRequest, writerNumber);
    postId = writePostResponse.getPostId();
    loginUser = (LoginInfo) userDetailsService.loadUserByUsername(writerId);
  }

  @DisplayName("?????? ?????? ?????????")
  @Test
  public void addComment() throws Exception {
    AddCommentRequest addCommentRequest = new AddCommentRequest(postId, writerNumber, "?????? ???????????? ??????????????????.");
    mvc.perform(MockMvcRequestBuilders.post("/api/comment").with(user(loginUser)).contentType("application/json")
        .content(mapper.writeValueAsString(addCommentRequest))).andExpect(status().isOk()).andDo(print());
  }

  @DisplayName("?????? ?????? ?????????")
  @Test
  public void deleteComment() throws Exception {
    AddCommentRequest addCommentRequest = new AddCommentRequest(postId, writerNumber, "?????? ???????????? ??????????????????.");
    AddCommentResponse addCommentResponse = commentService.addComment(addCommentRequest);

    DeleteCommentRequest deleteCommentRequest = new DeleteCommentRequest(addCommentResponse.getCommentId());
    mvc.perform(MockMvcRequestBuilders.delete("/api/comment").with(user(loginUser)).contentType("application/json")
        .content(mapper.writeValueAsString(deleteCommentRequest))).andExpect(status().isOk()).andDo(print());
  }

  @DisplayName("?????? ?????? ?????????")
  @Test
  public void updateComment() throws Exception {
    AddCommentRequest addCommentRequest = new AddCommentRequest(postId, writerNumber, "?????? ???????????? ??????????????????.");
    AddCommentResponse addCommentResponse = commentService.addComment(addCommentRequest);

    UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest(addCommentResponse.getCommentId(), "?????? ?????? API ????????? ?????????.");
    mvc.perform(MockMvcRequestBuilders.patch("/api/comment").with(user(loginUser)).contentType("application/json")
        .content(mapper.writeValueAsString(updateCommentRequest))).andExpect(status().isOk()).andDo(print());
  }
}
