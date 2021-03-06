package com.deu.football_love.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.AddBoardResponse;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.service.BoardService;
import com.deu.football_love.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BoardControllerTest {

  @Autowired
  private MemberService memberService;

  @Autowired
  private BoardService boardService;

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  MockMvc mvc;

  ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

  final String BASE_URL = "/api/board";
  String userId;

  @BeforeEach
  public void before() {
    MemberJoinRequest member =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp@naver.com").phone("010-1111-2222").type(MemberType.ROLE_ADMIN).build();
    memberService.join(member);
    userId = member.getId();
  }

  @DisplayName("board List??????")
  @Test
  public void getBoardList() throws Exception {
    UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
    AddBoardRequest request1 = new AddBoardRequest("?????????1", BoardType.GENERAL);
    AddBoardRequest request2 = new AddBoardRequest("?????????2", BoardType.GENERAL);
    AddBoardRequest request3 = new AddBoardRequest("?????????3", BoardType.GENERAL);
    boardService.add(request1);
    boardService.add(request2);
    boardService.add(request3);
    mvc.perform(MockMvcRequestBuilders.get(BASE_URL).contentType(MediaType.APPLICATION_JSON).with(csrf()).with(user(userDetails)))
        .andExpect(status().isOk()).andDo(print());
  }

  @DisplayName("????????? ????????? ??????")
  @Test
  public void addBoardSuccess() throws Exception {
    AddBoardRequest request = new AddBoardRequest("?????????", BoardType.GENERAL);
    UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

    mvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)).with(csrf())
        .with(user(userDetails))).andExpect(status().isOk()).andDo(print());
  }

  @DisplayName("?????? ?????????")
  @Test
  public void addBoardFailed() throws Exception {
    MemberJoinRequest member2 =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    memberService.join(member2);

    AddBoardRequest request = new AddBoardRequest("?????????", BoardType.GENERAL);
    UserDetails userDetails = userDetailsService.loadUserByUsername(member2.getId());

    mvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)).with(csrf())
        .with(user(userDetails))).andExpect(status().isForbidden()).andDo(print());
  }

  @DisplayName("????????? ????????? ??????")
  @Test
  public void deleteBoardSuccess() throws Exception {
    AddBoardRequest request = new AddBoardRequest("?????????", BoardType.GENERAL);
    UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
    AddBoardResponse board = boardService.add(request);
    mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + board.getBoardId()).contentType(MediaType.APPLICATION_JSON).with(csrf())
        .with(user(userDetails))).andExpect(status().isOk()).andDo(print());
  }
}
