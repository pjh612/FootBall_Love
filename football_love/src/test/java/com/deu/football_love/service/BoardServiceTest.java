package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.AddBoardResponse;
import com.deu.football_love.dto.board.BoardDto;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.QueryTeamDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class BoardServiceTest {


    @Autowired
    private BoardService boardService;

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void addBoardTest()
    {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");

        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        System.out.println(response.getBoardId());

        BoardDto findBoard = boardService.findById(response.getBoardId());

        Assertions.assertEquals(response.getBoardId(), findBoard.getBoardId());
    }

    @Test
    public void deleteBoardTest()
    {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");

        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);

        boardService.delete(response.getBoardId());
        BoardDto findBoard = boardService.findById(response.getBoardId());
        Assertions.assertNull(findBoard);
    }
}