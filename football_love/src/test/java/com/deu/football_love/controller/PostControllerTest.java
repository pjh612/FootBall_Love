package com.deu.football_love.controller;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.BoardDto;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.post.WritePostRequest;
import com.deu.football_love.dto.post.WritePostResponse;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
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

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private BoardService boardService;

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
    public void before()
    {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA","1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        BoardDto findBoard = boardService.findById(boardService.add(new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId())).getBoardId());
        writerNumber = memberJoinResponse.getNumber();
        writerId = memberJoinResponse.getId();
        boardId = findBoard.getBoardId();
        teamId = teamA.getTeamId();

    }

    @DisplayName("글 쓰기 테스트")
    @Test
    public void addPost() throws Exception {
        WritePostRequest writePostRequest = new WritePostRequest(writerNumber, boardId, teamId, "title1", "hi", null);
        UserDetails userDetails = userDetailsService.loadUserByUsername(writerId);
        mvc.perform(multipart("/api/board/post")
                .param("title", writePostRequest.getTitle())
                .param("content", writePostRequest.getContent())
                .param("teamId", Long.toString(writePostRequest.getTeamId()))
                .param("boardId", Long.toString(writePostRequest.getBoardId()))
                .param("authorNumber", Long.toString(writerNumber))
                .with(user(userDetails))
        ).andExpect(status().isOk());
    }

    @DisplayName("글 추천 테스트")
    @Test
    public void likePost() throws Exception {
        WritePostRequest writePostRequest = new WritePostRequest(writerNumber, boardId, teamId, "title1", "hi", null);
        WritePostResponse writePostResponse = postService.writePost(writePostRequest);

        UserDetails userDetails = userDetailsService.loadUserByUsername(writerId);
        mvc.perform(MockMvcRequestBuilders.post("/api/post/{postId}/like", writePostResponse.getPostId())
                .with(user(userDetails))
        ).andExpect(status().isOk());
    }

    @DisplayName("글 추천 중복 테스트")
    @Test
    public void DuplicatingLikePost() throws Exception {
        WritePostRequest writePostRequest = new WritePostRequest(writerNumber, boardId, teamId, "title1", "hi", null);
        WritePostResponse writePostResponse = postService.writePost(writePostRequest);

        UserDetails userDetails = userDetailsService.loadUserByUsername(writerId);
        mvc.perform(MockMvcRequestBuilders.post("/api/post/{postId}/like", writePostResponse.getPostId())
                .with(user(userDetails))
        ).andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.post("/api/post/{postId}/like", writePostResponse.getPostId())
                .with(user(userDetails))
        ).andExpect(status().isConflict())
        .andDo(print());
    }
}
