package com.deu.football_love.service;

import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.dto.MemberDto;
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
/*
        Member memberA = new Member();
        Member memberB = new Member();
        memberA.setId("memberA");
        memberA.setName("jinhyungPark");
        memberA.setPwd(passwordEncoder.encode("1234"));
        memberB.setId("memberB");
        memberB.setName("jinhyungLee");
        memberB.setPwd(passwordEncoder.encode("1234"));
        MemberDto memberADto = new MemberDto(memberA);
        MemberDto memberBDto = new MemberDto(memberB);
        memberService.join(memberADto);
        memberService.join(memberBDto);
        teamService.createNewTeam(memberADto, "teamA");
*/

    }

    @Test
    public void 팀_생성_테스트() {
        Member memberA = new Member();
        Member memberB = new Member();
        memberA.setId("memberA");
        memberA.setName("jinhyungPark");
        memberA.setPwd(passwordEncoder.encode("1234"));
        memberB.setId("memberB");
        memberB.setName("jinhyungLee");
        memberB.setPwd(passwordEncoder.encode("1234"));
        MemberDto memberADto = new MemberDto(memberA);
        MemberDto memberBDto = new MemberDto(memberB);
        memberService.join(memberADto);
        memberService.join(memberBDto);
        teamService.createNewTeam(memberADto, "teamA");

        assertNotNull( teamService.findTeam("teamA"));
        assertEquals(1, teamService.findTeamMember("teamA","memberA").size());
    }

    /**
     * 회원이 삭제되면 TeamMember도 삭제되는지 확인하는 테스트
     */
    @Test
    public void 회원_탈퇴_테스트() {
        Member memberA = new Member();
        Member memberB = new Member();
        memberA.setId("memberA");
        memberA.setName("jinhyungPark");
        memberA.setPwd(passwordEncoder.encode("1234"));
        memberB.setId("memberB");
        memberB.setName("jinhyungLee");
        memberB.setPwd(passwordEncoder.encode("1234"));
        MemberDto memberADto = new MemberDto(memberA);
        MemberDto memberBDto = new MemberDto(memberB);
        memberService.join(memberADto);
        memberService.join(memberBDto);
        teamService.createNewTeam(memberADto, "teamA");

        //List<TeamMember> teamMembers = teamRepository.selectTeamMember("teamA", "memberA");
        teamService.withdrawal("teamA","memberA");
        log.info("teamA = {}",teamService.findTeam("teamA"));
        assertNotNull(teamService.findTeam("teamA"));
        assertEquals(0,teamService.findTeamMember("teamA","memberA").size());
    }

    @Test
    public void 회원_팀탈퇴_테스트() {
        Member memberA = new Member();
        Member memberB = new Member();
        memberA.setId("memberA");
        memberA.setName("jinhyungPark");
        memberA.setPwd(passwordEncoder.encode("1234"));
        memberB.setId("memberB");
        memberB.setName("jinhyungLee");
        memberB.setPwd(passwordEncoder.encode("1234"));
        MemberDto memberADto = new MemberDto(memberA);
        MemberDto memberBDto = new MemberDto(memberB);
        memberService.join(memberADto);
        memberService.join(memberBDto);

        teamService.createNewTeam(memberADto, "teamA");
        em.flush();
        Team teamA = teamRepository.selectTeam("teamA");
        TeamMember teamMemberB = new TeamMember(teamA, memberB, AuthorityType.MEMBER);

        teamRepository.insertNewTeamMember(teamMemberB);
        teamService.withdrawal("teamA", "memberA");
      List<TeamMember> findTeamMemberA = teamRepository.selectTeamMember("teamA", "memberA");
      List<TeamMember> findTeamMemberB = teamRepository.selectTeamMember("teamA", "memberB");

        assertEquals(1, teamRepository.selectTeamMember("teamA", null).size());
        assertEquals(0, findTeamMemberA.size());
        assertEquals(1, findTeamMemberB.size());
        assertNotNull(teamRepository.selectTeam("teamA"));
        assertNotNull(memberRepository.selectMember("memberA"));
    }

    @Test
    public void 팀해체_테스트() {
        Member memberA = new Member();
        Member memberB = new Member();
        memberA.setId("memberA");
        memberA.setName("jinhyungPark");
        memberA.setPwd(passwordEncoder.encode("1234"));
        memberB.setId("memberB");
        memberB.setName("jinhyungLee");
        memberB.setPwd(passwordEncoder.encode("1234"));
        MemberDto memberADto = new MemberDto(memberA);
        MemberDto memberBDto = new MemberDto(memberB);
        memberService.join(memberADto);
        memberService.join(memberBDto);

        teamService.createNewTeam(memberADto, "teamA");
        em.flush();
        Team teamA = teamRepository.selectTeam("teamA");
        TeamMember teamMemberB = new TeamMember(teamA, memberB, AuthorityType.MEMBER);

        teamService.disbandmentTeam("teamA");

//        assertEquals(0, teamRepository.selectTeamMember("teamA", null).size());
        assertNull(teamRepository.selectTeam("teamA"));
        assertNotNull(memberRepository.selectMember("memberA"));
        assertNotNull(memberRepository.selectMember("memberB"));
    }
}