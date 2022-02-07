package com.deu.football_love.repository;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
    public void TeamMemberExistsTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", "1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh612@naver.com", "01012341234", MemberType.NORMAL);
        MemberJoinRequest memberBDto = new MemberJoinRequest("memberB", "1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto joinResponse = memberService.join(memberADto);
        QueryMemberDto joinResponse2 = memberService.join(memberBDto);
        CreateTeamResponse createTeamResponse = teamService.createNewTeam(memberADto.getId(), "teamA");

        /*TeamMember newTeamMember = new TeamMember(teamRepository.findByName("teamA").get(), memberRepository.selectMemberById("memberB"), TeamMemberType.COMMON);*/
        //teamMemberRepository.save(newTeamMember);
        Assertions.assertTrue(teamMemberRepository.existsByTeamIdAndMemberId(createTeamResponse.getTeamId(), joinResponse.getId()));
        Assertions.assertFalse(teamMemberRepository.existsByTeamIdAndMemberId(createTeamResponse.getTeamId(), joinResponse2.getId()));
    }
}
