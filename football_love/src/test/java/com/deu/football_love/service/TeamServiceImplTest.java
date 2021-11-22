package com.deu.football_love.service;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.dto.JoinRequest;
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

       JoinRequest memberADto = new JoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark",LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234");
        JoinRequest memberBDto = new JoinRequest("memberB", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark",LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234");
        memberService.join(memberADto);
        memberService.join(memberBDto);
        teamService.createNewTeam(memberADto.getId(), "teamA");

    }

    @Test
    public void 팀_생성_테스트() {

        TeamDto findTeam = teamService.findTeamByName("teamA");
        assertNotNull( findTeam);
        assertEquals(1, teamService.findTeamMember(findTeam.getId(),"memberA").size());
    }

    /**
     * 회원이 삭제되면 TeamMember도 삭제되는지 확인하는 테스트
     */
    @Test
    public void 회원_탈퇴_테스트() {
        TeamDto findTeam = teamService.findTeamByName("teamA");
        teamService.withdrawal(findTeam.getId(),"memberA");

        assertNotNull(findTeam);
        assertEquals(0,teamService.findTeamMember(findTeam.getId(), "memberA").size());
    }

    @Test
    public void 회원_팀탈퇴_테스트() {
        Team teamA = teamRepository.selectTeamByName("teamA");
        Member memberB = memberRepository.selectMember("memberB");
        TeamMember teamMemberB = new TeamMember(teamA, memberB, AuthorityType.MEMBER);

        teamRepository.insertNewTeamMember(teamMemberB);
        teamService.withdrawal(teamA.getId(), "memberA");
      List<TeamMember> findTeamMemberA = teamRepository.selectTeamMember(teamA.getId(), "memberA");
      List<TeamMember> findTeamMemberB = teamRepository.selectTeamMember(teamA.getId(), "memberB");

        assertEquals(1, teamRepository.selectTeamMember(teamA.getId(), null).size());
        assertEquals(0, findTeamMemberA.size());
        assertEquals(1, findTeamMemberB.size());
        assertNotNull(teamRepository.selectTeam(teamA.getId()));
        assertNotNull(memberRepository.selectMember("memberA"));
    }

    @Test
    public void 팀해체_테스트() {

        Team findTeam = teamRepository.selectTeamByName("teamA");

        teamService.applyToTeam(findTeam.getId(),"memberB", "hi");
        teamService.acceptApplication(findTeam.getId(),"memberB");
        teamService.disbandmentTeam(findTeam.getId());

        assertNull(teamRepository.selectTeam(findTeam.getId()));
        assertEquals(teamRepository.selectTeamMember(findTeam.getId(),"memberA").size(),0);
        assertEquals(teamRepository.selectTeamMember(findTeam.getId(),"memberB").size() ,0);
        assertNotNull(memberRepository.selectMember("memberA"));
        assertNotNull(memberRepository.selectMember("memberB"));
    }
}