package com.deu.football_love.service;

import com.deu.football_love.domain.ApplicationJoinTeam;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
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

    }

    @Test
    public void 팀_생성_테스트() {
        Member memberA = new Member();
        memberA.setId("memberA");
        memberA.setName("jinhyungPark");
        memberA.setPwd(passwordEncoder.encode("1234"));
        memberService.join(memberA);
        Team newTeam = new Team();
        newTeam.setCreateDate(LocalDate.now());
        newTeam.setName("teamA");
        TeamMember leader = new TeamMember(newTeam, memberA, AuthorityType.LEADER);
        newTeam.getTeamMembers().add(leader);
        teamService.createNewTeam(newTeam);

        assertEquals(1, teamService.findTeamMember("teamA","memberA").size());
    }

    /**
     * 회원이 삭제되면 TeamMember도 삭제되는지 확인하는 테스트
     */
    @Test
    @Rollback(false)
    public void 회원_탈퇴_테스트() {
        Member memberA = new Member();
        memberA.setId("memberA");
        memberA.setName("jinhyungPark");
        memberA.setPwd(passwordEncoder.encode("1234"));
        memberService.join(memberA);

        Team newTeam = new Team();
        newTeam.setCreateDate(LocalDate.now());
        newTeam.setName("teamA");
        TeamMember newTeamMember = new TeamMember(newTeam, memberA, AuthorityType.LEADER);
        newTeam.getTeamMembers().add(newTeamMember);
        teamService.createNewTeam(newTeam);
        //em.flush();
        //log.info("teamMember = {}",teamMember);
        //TeamMember teamMember = teamService.findTeamMember("teamA", "memberA").get(0);
        //em.remove(teamMember);

        //em.flush();
        Member findMember = memberService.findMember("memberA");
        findMember.getTeamMembers().forEach(teamMember -> {em.remove(teamMember);});
        em.remove(findMember);
        //assertNotNull(teamService.findTeam("teamA"));
        //assertEquals(0,teamService.findTeamMember("teamA","memberA").size());
    }

    @Test
    @Rollback(false)
    public void 회원_팀탈퇴_테스트() {
        Member memberA = new Member();
        Member memberB = new Member();
        memberA.setId("memberA");
        memberA.setName("jinhyungPark");
        memberA.setPwd(passwordEncoder.encode("1234"));
        memberB.setId("memberB");
        memberB.setName("jinhyungLee");
        memberB.setPwd(passwordEncoder.encode("1234"));
        memberService.join(memberA);
        memberService.join(memberB);

        Team newTeam = new Team();
        newTeam.setCreateDate(LocalDate.now());
        newTeam.setName("teamA");
        TeamMember leader = new TeamMember(newTeam, memberA, AuthorityType.LEADER);
        TeamMember teamMemberB = new TeamMember(newTeam, memberB, AuthorityType.MEMBER);
        newTeam.getTeamMembers().add(leader);
        newTeam.getTeamMembers().add(teamMemberB);

        teamService.createNewTeam(newTeam);
        teamService.withdrawal("teamA", "memberA");
        List<TeamMember> findTeamMemberA = teamRepository.selectTeamMember("teamA", "memberA");
        List<TeamMember> findTeamMemberB = teamRepository.selectTeamMember("teamA", "memberB");

        teamService.findTeamMember("teamA",null).forEach( member ->{
            log.info("member = {}",member.getMember().getId());
        });
        //assertEquals(1, teamRepository.selectTeamMember("teamA", null).size());
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
        memberB.setId("memberB");
        memberB.setName("jinhyungPark");
        em.persist(memberA);
        em.persist(memberB);

        Team newTeam = new Team();
        newTeam.setCreateDate(LocalDate.now());
        newTeam.setName("teamA");

        TeamMember teamMemberA = new TeamMember(newTeam, memberA, AuthorityType.LEADER);
        em.persist(teamMemberA);
        TeamMember teamMemberB = new TeamMember(newTeam, memberB, AuthorityType.LEADER);
        em.persist(teamMemberB);

        memberA.getTeamMembers().add(teamMemberA);
        memberA.getTeamMembers().add(teamMemberB);
        newTeam.getTeamMembers().add(teamMemberA);
        newTeam.getTeamMembers().add(teamMemberB);

        Team teamA = teamRepository.selectTeam("teamA");
        teamRepository.deleteTeam(teamA);

        assertEquals(0, teamRepository.selectTeamMember("teamA", null).size());
        assertNull(teamRepository.selectTeam("teamA"));
        assertNotNull(memberRepository.selectMember("memberA"));
        assertNotNull(memberRepository.selectMember("memberB"));
    }
}