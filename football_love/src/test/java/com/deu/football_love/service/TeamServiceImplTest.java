package com.deu.football_love.service;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.JoinRequest;
import com.deu.football_love.dto.MemberResponse;
import com.deu.football_love.dto.TeamDto;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest
@Transactional
@Slf4j
public class TeamServiceImplTest {
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    TeamService teamService;

    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void init()
    {

       JoinRequest memberADto = new JoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark",LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234", MemberType.NORMAL);
        JoinRequest memberBDto = new JoinRequest("memberB", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark",LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234", MemberType.NORMAL);
        memberService.join(memberADto);
        memberService.join(memberBDto);
        teamService.createNewTeam(memberADto.getId(), "teamA");

    }

    @Test
    public void 팀_생성_테스트() {

        TeamDto findTeam = teamService.findTeamByName("teamA");
        MemberResponse memberA = memberService.findMemberById("memberA");
        assertNotNull(findTeam);
        assertEquals(1, teamService.findTeamMember(findTeam.getId(),memberA.getNumber()).size());
    }

    /**
     * 회원이 삭제되면 TeamMember도 삭제되는지 확인하는 테스트
     */
    @Test
    public void 회원_탈퇴_테스트() {
        TeamDto findTeam = teamService.findTeamByName("teamA");

        MemberResponse memberA = memberService.findMemberById("memberA");
        teamService.withdrawal(findTeam.getId(),"memberA");

        assertNotNull(findTeam);
        assertEquals(0,teamService.findTeamMember(findTeam.getId(), memberA.getNumber()).size());
    }

    @Test
    public void 회원_팀탈퇴_테스트() {
        Team teamA = teamRepository.selectTeamByName("teamA");
        Member memberB = memberRepository.selectMemberById("memberB");
        TeamMember teamMemberB = new TeamMember(teamA, memberB, AuthorityType.MEMBER);

        teamRepository.insertNewTeamMember(teamMemberB);
        teamService.withdrawal(teamA.getId(), "memberA");

        MemberResponse findMemberA = memberService.findMemberById("memberA");
        MemberResponse findMemberB = memberService.findMemberById("memberB");
        List<TeamMember> findTeamMemberA = teamRepository.selectTeamMember(teamA.getId(), findMemberA.getNumber());
      List<TeamMember> findTeamMemberB = teamRepository.selectTeamMember(teamA.getId(), findMemberB.getNumber());

        assertEquals(1, teamRepository.selectTeamMember(teamA.getId(), null).size());
        assertEquals(0, findTeamMemberA.size());
        assertEquals(1, findTeamMemberB.size());
        assertNotNull(teamRepository.selectTeam(teamA.getId()));
        assertNotNull(memberRepository.selectMemberById("memberA"));
    }

    @Test
    public void 팀해체_테스트() {

        Team findTeam = teamRepository.selectTeamByName("teamA");

        teamService.applyToTeam(findTeam.getId(),"memberB", "hi");
        teamService.acceptApplication(findTeam.getId(),"memberB");
        teamService.disbandmentTeam(findTeam.getId());

        MemberResponse memberA = memberService.findMemberById("memberA");
        MemberResponse memberB = memberService.findMemberById("memberB");
        assertNull(teamRepository.selectTeam(findTeam.getId()));
        assertEquals(teamRepository.selectTeamMember(findTeam.getId(),memberA.getNumber()).size(),0);
        assertEquals(teamRepository.selectTeamMember(findTeam.getId(),memberB.getNumber()).size() ,0);
        assertNotNull(memberRepository.selectMemberById("memberA"));
        assertNotNull(memberRepository.selectMemberById("memberB"));
    }
}