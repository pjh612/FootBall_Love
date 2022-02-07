package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamMember;
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
import com.deu.football_love.dto.team.QueryTeamMemberDto;
import com.deu.football_love.exception.NotTeamMemberException;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.TeamMemberRepository;
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

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Slf4j
public class TeamServiceTest {
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamMemberRepository teamMemberRepository;

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

        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", "1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh612@naver.com", "01012341234", MemberType.NORMAL);
        MemberJoinRequest memberBDto = new MemberJoinRequest("memberB", "1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        memberService.join(memberADto);
        memberService.join(memberBDto);
        teamService.createNewTeam(memberADto.getId(), "teamA");

    }

    @Test
    public void findTeamByTeamNameTest() {
        QueryTeamDto teamA = teamService.findTeamByName("teamA");
        boardService.add(new AddBoardRequest("공지사항", BoardType.NOTICE, teamA.getId()));
        boardService.add(new AddBoardRequest("자유 게시판", BoardType.GENERAL, teamA.getId()));

        QueryTeamDto findTeam = teamService.findTeamByName("teamA");

        Assertions.assertEquals(2, findTeam.getBoards().size());
        Assertions.assertEquals("공지사항", findTeam.getBoards().get(0).getBoardName());
        Assertions.assertEquals("자유 게시판", findTeam.getBoards().get(1).getBoardName());
        Assertions.assertEquals("teamA", findTeam.getName());
        Assertions.assertEquals(1, findTeam.getTeamMembers().size());
        Assertions.assertEquals("memberA", findTeam.getTeamMembers().get(0));
    }

    @Test
    public void findTeamMemberByMemberIdTest() {
        QueryTeamDto findTeam = teamService.findTeamByName("teamA");

        assertDoesNotThrow(() -> teamService.findTeamMemberByTeamIdAndMemberId(findTeam.getId(), "memberA"));
    }

    @Test
    public void 팀_생성_테스트() {

        QueryTeamDto findTeam = teamService.findTeamByName("teamA");
        QueryMemberDto memberA = memberService.findMemberById("memberA");
        assertNotNull(findTeam);
        assertDoesNotThrow(() -> teamService.findTeamMemberByTeamIdAndMemberNumber(findTeam.getId(), memberA.getNumber()));
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
        assertThrows(NotTeamMemberException.class, () -> teamService.findTeamMemberByTeamIdAndMemberNumber(findTeam.getId(), memberA.getNumber()));
    }

    @Test
    public void 회원_팀탈퇴_테스트() {
        Team teamA = teamRepository.findByName("teamA").get();
        Member memberB = memberRepository.selectMemberById("memberB");
        TeamMember teamMemberB = new TeamMember(teamA, memberB, TeamMemberType.COMMON);

        teamMemberRepository.save(teamMemberB);
        teamService.withdrawal(teamA.getId(), "memberA");

        QueryMemberDto findMemberA = memberService.findMemberById("memberA");
        QueryMemberDto findMemberB = memberService.findMemberById("memberB");
        ;


        assertEquals(1, teamMemberRepository.findTeamMembersByTeamId(teamA.getId()).size());
        assertFalse(teamMemberRepository.findByTeamIdAndMemberNumber(teamA.getId(), findMemberA.getNumber()).isPresent());
        assertTrue(teamMemberRepository.findByTeamIdAndMemberNumber(teamA.getId(), findMemberB.getNumber()).isPresent());
        assertNotNull(teamRepository.findById(teamA.getId()));
        assertNotNull(memberRepository.selectMemberById("memberA"));
    }

    @Test
    public void 팀해체_테스트() {

        Team findTeam = teamRepository.findByName("teamA").get();
        long teamId = findTeam.getId();
        teamService.applyToTeam(findTeam.getId(), "memberB", "hi");
        teamService.acceptApplication(findTeam.getId(), "memberB");

        QueryMemberDto memberA = memberService.findMemberById("memberA");
        QueryMemberDto memberB = memberService.findMemberById("memberB");
        AddBoardResponse boardA = boardService.add(new AddBoardRequest("boardA", BoardType.NOTICE, teamId));
        postService.writePost(new WritePostRequest(memberA.getNumber(), boardA.getBoardId(), teamId, "hello", "content", null));
        postService.writePost(new WritePostRequest(memberB.getNumber(), boardA.getBoardId(), teamId, "hello", "content", null));
        teamService.disbandmentTeam(findTeam.getId());


        assertFalse(teamRepository.findById(findTeam.getId()).isPresent());
        assertFalse(teamMemberRepository.findByTeamIdAndMemberNumber(teamId, memberA.getNumber()).isPresent());
        assertFalse(teamMemberRepository.findByTeamIdAndMemberNumber(teamId, memberB.getNumber()).isPresent());
        assertNull(boardService.findById(boardA.getBoardId()));
        assertEquals(0, postService.findAllPostsByBoardId(boardA.getBoardId(), null).getSize());
        assertNotNull(memberRepository.selectMemberById("memberA"));
        assertNotNull(memberRepository.selectMemberById("memberB"));
    }

    @Test
    public void 팀해제Cascade_테스트() {
        Member findMember = memberRepository.selectMemberById("memberA");
        Team findTeam = teamRepository.findByName("teamA").orElseThrow(() -> new IllegalArgumentException("no such team data"));
        teamService.applyToTeam(findTeam.getId(), "memberB", "hi");
        teamService.acceptApplication(findTeam.getId(), "memberB");
        AddBoardResponse addBoardResponse = boardService.add(new AddBoardRequest("공지사항", BoardType.NOTICE, findTeam.getId()));
        WritePostResponse post1 = postService.writePost(new WritePostRequest(findMember.getNumber(), addBoardResponse.getBoardId(), findTeam.getId(), "안녕하세요", "가입인사 합니다.", null));
        WritePostResponse post2 = postService.writePost(new WritePostRequest(findMember.getNumber(), addBoardResponse.getBoardId(), findTeam.getId(), "안녕하세요 팀장입니다.", "내일 집합이요", null));

        teamService.disbandmentTeam(findTeam.getId());

        assertEquals(0, teamService.findTeamMembersByTeamId(findTeam.getId()).size());
        assertThrows(IllegalArgumentException.class, () -> teamService.findTeamByName("teamA"));
        assertNull(boardService.findById(addBoardResponse.getBoardId()));
        assertEquals(0, findTeam.getBoards().size());
        assertEquals(0, findMember.getPosts().size());
        assertThrows(IllegalArgumentException.class, () -> postService.findPost(post1.getPostId()));
        assertThrows(IllegalArgumentException.class, () -> postService.findPost(post2.getPostId()));
    }


}