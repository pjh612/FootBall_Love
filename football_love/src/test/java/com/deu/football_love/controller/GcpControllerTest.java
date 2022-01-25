package com.deu.football_love.controller;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.BoardDto;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.post.QueryPostDto;
import com.deu.football_love.dto.team.CreateTeamRequest;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.service.BoardService;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.PostService;
import com.deu.football_love.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional

public class GcpControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    MemberService memberService;

    @Autowired
    TeamService teamService;

    @Autowired
    BoardService boardService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PostService postService;

    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void postImageTest() throws Exception {
        Address address = new Address("busan", "guemgangro", "46233");

        //회원 가입
        MemberJoinRequest request = new MemberJoinRequest("pjh612", "123", "jhjh", "박진형",
                null, address, "pjh_jn@naver.com", "010-2104-2419", MemberType.NORMAL);
        QueryMemberDto join = memberService.join(request);
        CreateTeamRequest createTeamRequest = new CreateTeamRequest("FC진형");
        UserDetails userDetails = userDetailsService.loadUserByUsername(join.getId());
        //팀 만들기
        mvc.perform(MockMvcRequestBuilders.post("/api/team")
                .with(user(userDetails))
                .contentType("application/json")
                .content(mapper.writeValueAsString(createTeamRequest))
        ).andExpect(status().isOk());


        // 게시판 만들기
        QueryTeamDto findTeam = teamService.findTeamByName("FC진형");
        AddBoardRequest addBoardRequest = new AddBoardRequest("공지사항", BoardType.NOTICE, findTeam.getId());
        mvc.perform(MockMvcRequestBuilders.post("/api/team/{teamId}/board", findTeam.getId())
                .with(user(userDetails))
                .contentType("application/json")
                .content(mapper.writeValueAsString(addBoardRequest))
        ).andExpect(status().isOk()).andDo(print());


        //게시판 글쓰기
        BoardDto findBoard = boardService.findByTeamIdAndBoardName(findTeam.getId(), "공지사항");
        String title = "제목 입니다.";
        String content = "내용 입니다.";
        File file = new File("src/test/resources/test.JPG");
        String mimeType = Files.probeContentType(file.toPath());
        MockMultipartFile image = new MockMultipartFile("images[0]", "test.JPG", mimeType,new FileInputStream(new File("src/test/resources/test.JPG")));
        mvc.perform(multipart("/api/board/post")
                .file(image)
                .param("title", title)
                .param("content",content)
                .param("teamId",findTeam.getId().toString())
                .param("boardId", findBoard.getBoardId().toString())
                .param("authorNumber", join.getNumber().toString())
                .with(user(userDetails))
        ).andExpect(status().isOk());

        List<QueryPostDto> postList = postService.findAllPostsByBoardId(findBoard.getBoardId());
        for (QueryPostDto post : postList) {
            Assertions.assertThat(post.getAuthorId()).isEqualTo("pjh612");
        }

    }
}
