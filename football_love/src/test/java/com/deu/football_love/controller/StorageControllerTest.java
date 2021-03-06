package com.deu.football_love.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
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
import com.deu.football_love.dto.post.QueryPostDto;
import com.deu.football_love.dto.team.CreateTeamRequest;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamBoardService;
import com.deu.football_love.service.PostService;
import com.deu.football_love.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional

public class StorageControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  MemberService memberService;

  @Autowired
  TeamService teamService;

  @Autowired
  TeamBoardService boardService;

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  PostService postService;

  ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

  @Test
  public void postImageTest() throws Exception {
    Address address = new Address("busan", "guemgangro", "46233");

    // ?????? ??????
    MemberJoinRequest request =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto join = memberService.join(request);
    CreateTeamRequest createTeamRequest = new CreateTeamRequest("FC??????", "FC ?????? ?????????");
    UserDetails userDetails = userDetailsService.loadUserByUsername(join.getId());
    // ??? ?????????
    mvc.perform(MockMvcRequestBuilders.post("/api/team").with(user(userDetails)).contentType("application/json")
        .content(mapper.writeValueAsString(createTeamRequest))).andExpect(status().isOk());

    // ????????? ?????????
    QueryTeamDto findTeam = teamService.findTeamByName("FC??????");
    AddTeamBoardRequest addBoardRequest = new AddTeamBoardRequest("????????????", BoardType.NOTICE, findTeam.getId());
    mvc.perform(MockMvcRequestBuilders.post("/api/team/{teamId}/board", findTeam.getId()).with(user(userDetails)).contentType("application/json")
        .content(mapper.writeValueAsString(addBoardRequest))).andExpect(status().isOk()).andDo(print());

    // ????????? ?????????
    TeamBoardDto findBoard = boardService.findByTeamIdAndBoardName(findTeam.getId(), "????????????");
    String title = "?????? ?????????.";
    String content = "?????? ?????????.";
    File file = new File("src/test/resources/test.JPG");
    String mimeType = Files.probeContentType(file.toPath());
    MockMultipartFile image = new MockMultipartFile("images[0]", "test.JPG", mimeType, new FileInputStream(new File("src/test/resources/test.JPG")));
    mvc.perform(multipart("/api/board/post")
        .file(image).param("title", title)
        .param("content", content)
        .param("teamId", findTeam.getId().toString())
        .param("boardId", findBoard.getBoardId().toString())
        .param("authorNumber", join.getNumber().toString()).with(user(userDetails)))
        .andExpect(status().isOk());

    Page<QueryPostDto> postList = postService.findAllPostsByBoardId(findBoard.getBoardId(), null);
    for (QueryPostDto post : postList) {
      Assertions.assertThat(post.getAuthorId()).isEqualTo("dbtlwns");
    }

  }
}
