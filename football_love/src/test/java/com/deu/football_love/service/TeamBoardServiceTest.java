package com.deu.football_love.service;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class BoardServiceTest {


  @Autowired
  private BoardService boardService;

  @Autowired
  private TeamService teamService;

  @Autowired
  private MemberService memberService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void addBoardTest() {
    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_NORMAL).build();
    memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "팀 A 소개");

    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
    AddBoardResponse response = boardService.add(request);
    BoardDto findBoard = boardService.findById(response.getBoardId());

    Assertions.assertEquals(response.getBoardId(), findBoard.getBoardId());
  }

  @Test
  public void deleteBoardTest() {
    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_NORMAL).build();
    memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "팀 A 소개");

    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
    AddBoardResponse response = boardService.add(request);

        boardService.delete(response.getBoardId());
        Assertions.assertThrows(IllegalArgumentException.class, ()->boardService.findById(response.getBoardId()));
    }
}

