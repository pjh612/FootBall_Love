package com.deu.football_love.repository;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class TeamMemberRepositoryTest {

  @Autowired
  private TeamMemberRepository teamMemberRepository;

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private TeamService teamService;
  @Autowired
  private MemberService memberService;

  @Test
  @Rollback(value = false)
  public void TeamMemberExistsTest() {

    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.NORMAL).build();
    MemberJoinRequest memberBDto = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns2")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp2@naver.com").phone("010-1111-2222")
        .type(MemberType.NORMAL).build();
    QueryMemberDto joinResponse = memberService.join(memberADto);
    QueryMemberDto joinResponse2 = memberService.join(memberBDto);
    CreateTeamResponse createTeamResponse = teamService.createNewTeam(memberADto.getId(), "teamA");

    /*
     * TeamMember newTeamMember = new TeamMember(teamRepository.findByName("teamA").get(),
     * memberRepository.selectMemberById("memberB"), TeamMemberType.COMMON);
     */
    // teamMemberRepository.save(newTeamMember);
    Assertions.assertTrue(teamMemberRepository
        .existsByTeamIdAndMemberId(createTeamResponse.getTeamId(), joinResponse.getId()));
    Assertions.assertFalse(teamMemberRepository
        .existsByTeamIdAndMemberId(createTeamResponse.getTeamId(), joinResponse2.getId()));
  }
}
