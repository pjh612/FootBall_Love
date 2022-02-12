package com.deu.football_love.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;

import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.QueryTeamListItemDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
import com.deu.football_love.exception.NotTeamMemberException;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.TeamMemberRepository;
import com.deu.football_love.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;


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

    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("memberA")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.NORMAL).build();
    MemberJoinRequest memberBDto = MemberJoinRequest.memberJoinRequestBuilder().id("memberB")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp2@naver.com").phone("010-1111-2222")
        .type(MemberType.NORMAL).build();;
        memberService.join(memberADto);
        memberService.join(memberBDto);
        teamService.createNewTeam(memberADto.getId(), "teamA","팀 소개 입니다.");

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
        Member memberB = memberRepository.findById("memberB").get();
        TeamMember teamMemberB = new TeamMember(teamA, memberB, TeamMemberType.COMMON);

        teamMemberRepository.save(teamMemberB);
        teamService.withdrawal(teamA.getId(), "memberA");

        QueryMemberDto findMemberA = memberService.findMemberById("memberA");
        QueryMemberDto findMemberB = memberService.findMemberById("memberB");
        assertEquals(1, teamMemberRepository.findTeamMembersByTeamId(teamA.getId()).size());
        assertFalse(teamMemberRepository.findByTeamIdAndMemberNumber(teamA.getId(), findMemberA.getNumber()).isPresent());
        assertTrue(teamMemberRepository.findByTeamIdAndMemberNumber(teamA.getId(), findMemberB.getNumber()).isPresent());
        assertNotNull(teamRepository.findById(teamA.getId()));
        assertNotNull(memberRepository.findById("memberA").get());
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
        assertThrows(IllegalArgumentException.class, () -> boardService.findById(boardA.getBoardId()));
        assertEquals(0, postService.findAllPostsByBoardId(boardA.getBoardId(), null).getSize());
        assertNotNull(memberRepository.findById("memberA").get());
        assertNotNull(memberRepository.findById("memberB").get());
    }

    @Test
    public void 팀해제Cascade_테스트() {
        Member findMember = memberRepository.findById("memberA").get();
        Team findTeam = teamRepository.findByName("teamA").orElseThrow(() -> new IllegalArgumentException("no such team data"));
        teamService.applyToTeam(findTeam.getId(), "memberB", "hi");
        teamService.acceptApplication(findTeam.getId(), "memberB");
        AddBoardResponse addBoardResponse = boardService.add(new AddBoardRequest("공지사항", BoardType.NOTICE, findTeam.getId()));
        WritePostResponse post1 = postService.writePost(new WritePostRequest(findMember.getNumber(), addBoardResponse.getBoardId(), findTeam.getId(), "안녕하세요", "가입인사 합니다.", null));
        WritePostResponse post2 = postService.writePost(new WritePostRequest(findMember.getNumber(), addBoardResponse.getBoardId(), findTeam.getId(), "안녕하세요 팀장입니다.", "내일 집합이요", null));

        teamService.disbandmentTeam(findTeam.getId());

        assertEquals(0, teamService.findTeamMembersByTeamId(findTeam.getId()).size());
        assertThrows(IllegalArgumentException.class, () -> teamService.findTeamByName("teamA"));
        assertThrows(IllegalArgumentException.class, () -> boardService.findById(addBoardResponse.getBoardId()));
        assertEquals(0, findTeam.getBoards().size());
        assertEquals(0, findMember.getPosts().size());
        assertThrows(IllegalArgumentException.class, () -> postService.findPost(post1.getPostId()));
        assertThrows(IllegalArgumentException.class, () -> postService.findPost(post2.getPostId()));
    }

    /**
     * teamA(memberA) pjh
     * teamC(PJH) kjh memberA
     * teamD(KJH) pjh
     */
    @Test
    public void 회원소속_팀_전체조회()
    {
        MemberJoinRequest joinPJH = MemberJoinRequest.memberJoinRequestBuilder().id("pjh")
                .name("박진형").pwd("1234").nickname("박진형닉네임").address(new Address("양산", "행복길", "11"))
                .birth(LocalDate.of(2000, 1, 1)).email("fblpjh@naver.com").phone("010-1111-2222")
                .type(MemberType.NORMAL).build();
        MemberJoinRequest joinKJH = MemberJoinRequest.memberJoinRequestBuilder().id("kjh")
                .name("김진형").pwd("1234").nickname("김진형닉네임").address(new Address("양산", "행복길", "11"))
                .birth(LocalDate.of(2000, 1, 1)).email("fblkjh@naver.com").phone("010-1111-2222")
                .type(MemberType.NORMAL).build();
        QueryMemberDto findPJH = memberService.join(joinPJH);
        memberService.join(joinKJH);
        QueryTeamDto teamA = teamService.findTeamByName("teamA");
        CreateTeamResponse teamC = teamService.createNewTeam(joinPJH.getId(), "teamC","팀 C 소개");
        CreateTeamResponse teamD = teamService.createNewTeam(joinKJH.getId(), "teamD","팀 D 소개");

        teamService.applyToTeam(teamC.getTeamId(), "kjh", "hi");
        teamService.acceptApplication(teamC.getTeamId(), "kjh");

        teamService.applyToTeam(teamD.getTeamId(), "pjh", "hi");
        teamService.acceptApplication(teamD.getTeamId(), "pjh");

        teamService.applyToTeam(teamA.getId(), "pjh", "hi");
        teamService.acceptApplication(teamA.getId(), "pjh");

        teamService.applyToTeam(teamC.getTeamId(), "memberA", "hi");
        teamService.acceptApplication(teamC.getTeamId(), "memberA");

        log.info("########################### 쿼리 시작 #############################");
        em.flush();
        em.clear();
        List<QueryTeamListItemDto> result = teamService.findAllTeamByMemberNumber(findPJH.getNumber());
        for (QueryTeamListItemDto item : result) {
            log.info("teamId={}, teamName={}, teamMemberTotal = {}, MyAuthority = {}",item.getTeamId(),item.getTeamName(),item.getTotalMemberCount(),item.getAuthority());
        }
    }

    /**
     * 프로필 사진 변경이 없을 때, 팀 소개가 잘 업데이트 되는지 확인
     */
    @Test
    public void 팀_프로필_업데이트_테스트()
    {
        QueryTeamDto findTeam = teamService.findTeamByName("teamA");
        Long teamId = findTeam.getId();
        String introduce = "수정된 팀 소개 입니다.";

        teamService.updateTeamProfile(teamId, null, introduce);
        QueryTeamDto team = teamService.findTeam(findTeam.getId());
        Assertions.assertEquals(introduce, team.getIntroduce());
    }

}