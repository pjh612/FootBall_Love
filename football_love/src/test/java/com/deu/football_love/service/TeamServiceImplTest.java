package com.deu.football_love.service;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.AddBoardResponse;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.post.WritePostRequest;
import com.deu.football_love.dto.post.WritePostResponse;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
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
    BoardService boardService;

    @Autowired
    PostService postService;

    @Autowired
    EntityManager em;


    @BeforeEach
    public void init() {

        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh612@naver.com", "01012341234", MemberType.NORMAL);
        MemberJoinRequest memberBDto = new MemberJoinRequest("memberB", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        memberService.join(memberADto);
        memberService.join(memberBDto);
        teamService.createNewTeam(memberADto.getId(), "teamA");

    }


   /* public void auditor_test() {

        MemberResponse member = memberService.login(new LoginRequest("memberA", "1234"));
        session.setAttribute(SessionConst.SESSION_MEMBER, member);
        TeamDto teamA = teamService.findTeamByName("teamA");

        System.out.println("teamA.getCreatedBy() = " + teamA.getCreatedBy());
        System.out.println("teamA.getLastModifiedBy() = " + teamA.getLastModifiedBy());
        System.out.println("teamA.getCreatedDate() = " + teamA.getCreatedDate());
        System.out.println("teamA.getLastModifiedDate() = " + teamA.getLastModifiedDate());
    }*/

    @Test
    public void 팀_생성_테스트() {

        QueryTeamDto findTeam = teamService.findTeamByName("teamA");
        QueryMemberDto memberA = memberService.findMemberById("memberA");
        assertNotNull(findTeam);
        assertEquals(1, teamService.findTeamMember(findTeam.getId(), memberA.getNumber()).size());
    }

    /**
     * 회원이 삭제되면 TeamMember도 삭제되는지 확인하는 테스트
     */
    @Test
    public void 회원_탈퇴_테스트() {
        QueryTeamDto findTeam = teamService.findTeamByName("teamA");

        QueryMemberDto memberA = memberService.findMemberById("memberA");
        teamService.withdrawal(findTeam.getId(), "memberA");

        assertNotNull(findTeam);
        assertEquals(0, teamService.findTeamMember(findTeam.getId(), memberA.getNumber()).size());
    }

    @Test
    public void 회원_팀탈퇴_테스트() {
        Team teamA = teamRepository.selectTeamByName("teamA");
        Member memberB = memberRepository.selectMemberById("memberB");
        TeamMember teamMemberB = new TeamMember(teamA, memberB, TeamMemberType.COMMON);

        teamRepository.insertNewTeamMember(teamMemberB);
        teamService.withdrawal(teamA.getId(), "memberA");

        QueryMemberDto findMemberA = memberService.findMemberById("memberA");
        QueryMemberDto findMemberB = memberService.findMemberById("memberB");
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
        long teamId = findTeam.getId();
        teamService.applyToTeam(findTeam.getId(), "memberB", "hi");
        teamService.acceptApplication(findTeam.getId(), "memberB");

        QueryMemberDto memberA = memberService.findMemberById("memberA");
        QueryMemberDto memberB = memberService.findMemberById("memberB");
        AddBoardResponse boardA = boardService.add(new AddBoardRequest("boardA", BoardType.NOTICE, teamId));
        postService.writePost(new WritePostRequest(memberA.getNumber(), boardA.getBoardId(), "hello", "content", null));
        postService.writePost(new WritePostRequest(memberB.getNumber(), boardA.getBoardId(), "hello", "content", null));
        teamService.disbandmentTeam(findTeam.getId());


        assertNull(teamRepository.selectTeam(findTeam.getId()));
        assertEquals(0, teamRepository.selectTeamMember(teamId, memberA.getNumber()).size());
        assertEquals(0, teamRepository.selectTeamMember(teamId, memberB.getNumber()).size());
        assertNull(boardService.findById(boardA.getBoardId()));
        assertEquals(0,postService.findAllPostsByBoardId(boardA.getBoardId()).size());
        assertNotNull(memberRepository.selectMemberById("memberA"));
        assertNotNull(memberRepository.selectMemberById("memberB"));
    }

    @Test
    public void 팀해제Cascade_테스트() {
        Member findMember = memberRepository.selectMemberById("memberA");
        Team findTeam = teamRepository.selectTeamByName("teamA");
        teamService.applyToTeam(findTeam.getId(), "memberB", "hi");
        teamService.acceptApplication(findTeam.getId(), "memberB");
        AddBoardResponse addBoardResponse = boardService.add(new AddBoardRequest("공지사항", BoardType.NOTICE, findTeam.getId()));
        WritePostResponse post1 = postService.writePost(new WritePostRequest(findMember.getNumber(), addBoardResponse.getBoardId(), "안녕하세요", "가입인사 합니다.", null));
        WritePostResponse post2 = postService.writePost(new WritePostRequest(findMember.getNumber(), addBoardResponse.getBoardId(), "안녕하세요 팀장입니다.", "내일 집합이요", null));

        teamService.disbandmentTeam(findTeam.getId());

        Assertions.assertEquals(0,teamService.findTeamMember(findTeam.getId(), null).size());
        Assertions.assertNull(teamService.findTeamByName("teamA"));
        Assertions.assertNull(boardService.findById(addBoardResponse.getBoardId()));
        Assertions.assertEquals(0, teamService.findTeamMember(findTeam.getId(), null).size());
        Assertions.assertEquals(0,findTeam.getBoards().size());
        Assertions.assertEquals(0,findMember.getPosts().size());
        Assertions.assertNull(postService.findPost(post1.getPostId()));
        Assertions.assertNull(postService.findPost(post2.getPostId()));
    }
}