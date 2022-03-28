package com.deu.football_love.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
import com.deu.football_love.dto.Teamboard.AddTeamBoardRequest;
import com.deu.football_love.dto.Teamboard.TeamBoardDto;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.post.WritePostResponse;
import com.deu.football_love.dto.post.WriteTeamPostRequest;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.PostService;
import com.deu.football_love.service.TeamBoardService;
import com.deu.football_love.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostControllerTest {

  @Autowired
  private MemberService memberService;

  @Autowired
  private TeamService teamService;

  @Autowired
  private TeamBoardService boardService;

  @Autowired
  private PostService postService;

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  MockMvc mvc;

  ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

  String writerId;
  Long writerNumber;
  Long boardId;
  Long teamId;

  @BeforeEach
  public void before() {
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns").name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "팀 A 소개");
    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    TeamBoardDto findBoard =
        boardService.findById(boardService.add(new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId())).getBoardId());
    writerNumber = memberJoinResponse.getNumber();
    writerId = memberJoinResponse.getId();
    boardId = findBoard.getBoardId();
    teamId = teamA.getTeamId();

  }

  @DisplayName("글 쓰기 테스트")
  @Test
  public void addPost() throws Exception {
    WriteTeamPostRequest writePostRequest = new WriteTeamPostRequest(boardId, teamId, "title1", "hi", null);
    UserDetails userDetails = userDetailsService.loadUserByUsername(writerId);
    mvc.perform(multipart("/api/board/team/post").param("title", writePostRequest.getTitle()).param("content", writePostRequest.getContent())
        .param("teamId", Long.toString(writePostRequest.getTeamId())).param("boardId", Long.toString(writePostRequest.getBoardId()))
        .with(user(userDetails))).andExpect(status().isOk());
  }

  @DisplayName("글 추천 테스트")
  @Test
  public void likePost() throws Exception {
    WriteTeamPostRequest writePostRequest = new WriteTeamPostRequest(boardId, teamId, "title1", "hi", null);
    WritePostResponse writePostResponse = postService.writeTeamPost(writePostRequest, writerNumber);

    UserDetails userDetails = userDetailsService.loadUserByUsername(writerId);
    mvc.perform(MockMvcRequestBuilders.post("/api/post/{postId}/like", writePostResponse.getPostId()).with(user(userDetails)))
        .andExpect(status().isOk());
  }

  @DisplayName("글 추천 중복 테스트")
  @Test
  public void DuplicatingLikePost() throws Exception {
    WriteTeamPostRequest writePostRequest = new WriteTeamPostRequest(boardId, teamId, "title1", "hi", null);
    WritePostResponse writePostResponse = postService.writeTeamPost(writePostRequest, writerNumber);

    UserDetails userDetails = userDetailsService.loadUserByUsername(writerId);
    mvc.perform(MockMvcRequestBuilders.post("/api/post/{postId}/like", writePostResponse.getPostId()).with(user(userDetails)))
        .andExpect(status().isOk());

    mvc.perform(MockMvcRequestBuilders.post("/api/post/{postId}/like", writePostResponse.getPostId()).with(user(userDetails)))
        .andExpect(status().isConflict()).andDo(print());
  }
}
