package com.deu.football_love.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.deu.football_love.exception.NotExistDataException;
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
import com.deu.football_love.dto.Teamboard.AddTeamBoardRequest;
import com.deu.football_love.dto.Teamboard.AddTeamBoardResponse;
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
    TeamBoardService boardService;

    @Autowired
    PostService postService;

    @Autowired
    EntityManager em;


    @BeforeEach
    public void init() {

    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("memberA")
        .name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_NORMAL).build();
    MemberJoinRequest memberBDto = MemberJoinRequest.memberJoinRequestBuilder().id("memberB")
        .name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp2@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_NORMAL).build();;
        memberService.join(memberADto);
        memberService.join(memberBDto);
        teamService.createNewTeam(memberADto.getId(), "teamA","??? ?????? ?????????.");

    }

    @Test
    public void findTeamByTeamNameTest() {
        QueryTeamDto teamA = teamService.findTeamByName("teamA");
        boardService.add(new AddTeamBoardRequest("????????????", BoardType.NOTICE, teamA.getId()));
        boardService.add(new AddTeamBoardRequest("?????? ?????????", BoardType.GENERAL, teamA.getId()));

        QueryTeamDto findTeam = teamService.findTeamByName("teamA");

        Assertions.assertEquals(2, findTeam.getBoards().size());
        Assertions.assertEquals("????????????", findTeam.getBoards().get(0).getBoardName());
        Assertions.assertEquals("?????? ?????????", findTeam.getBoards().get(1).getBoardName());
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
    public void ???_??????_?????????() {

        QueryTeamDto findTeam = teamService.findTeamByName("teamA");
        QueryMemberDto memberA = memberService.findMemberById("memberA");
        assertNotNull(findTeam);
        assertDoesNotThrow(() -> teamService.findTeamMemberByTeamIdAndMemberNumber(findTeam.getId(), memberA.getNumber()));
    }

    /**
     * ????????? ???????????? TeamMember??? ??????????????? ???????????? ?????????
     */
    @Test
    public void ??????_??????_?????????() {
        QueryTeamDto findTeam = teamService.findTeamByName("teamA");

        QueryMemberDto memberA = memberService.findMemberById("memberA");
        teamService.withdrawal(findTeam.getId(), "memberA");

        assertNotNull(findTeam);
        assertThrows(NotTeamMemberException.class, () -> teamService.findTeamMemberByTeamIdAndMemberNumber(findTeam.getId(), memberA.getNumber()));
    }

    @Test
    public void ??????_?????????_?????????() {
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
    public void ?????????_?????????() {

        Team findTeam = teamRepository.findByName("teamA").get();
        long teamId = findTeam.getId();
        teamService.applyToTeam(findTeam.getId(), "memberB", "hi");
        teamService.acceptApplication(findTeam.getId(), "memberB");

        QueryMemberDto memberA = memberService.findMemberById("memberA");
        QueryMemberDto memberB = memberService.findMemberById("memberB");
        AddTeamBoardResponse boardA = boardService.add(new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamId));
        postService.writePost(new WritePostRequest(boardA.getBoardId(), teamId, "hello", "content", null),memberA.getNumber());
        postService.writePost(new WritePostRequest(boardA.getBoardId(), teamId, "hello", "content", null),memberA.getNumber());
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
    public void ?????????Cascade_?????????() {
        Member findMember = memberRepository.findById("memberA").get();
        Team findTeam = teamRepository.findByName("teamA").orElseThrow(() -> new IllegalArgumentException("no such team data"));
        teamService.applyToTeam(findTeam.getId(), "memberB", "hi");
        teamService.acceptApplication(findTeam.getId(), "memberB");
        AddTeamBoardResponse addBoardResponse = boardService.add(new AddTeamBoardRequest("????????????", BoardType.NOTICE, findTeam.getId()));
        WritePostResponse post1 = postService.writePost(new WritePostRequest(addBoardResponse.getBoardId(), findTeam.getId(), "???????????????", "???????????? ?????????.", null),findMember.getNumber());
        WritePostResponse post2 = postService.writePost(new WritePostRequest(addBoardResponse.getBoardId(), findTeam.getId(), "??????????????? ???????????????.", "?????? ????????????", null),findMember.getNumber());

        teamService.disbandmentTeam(findTeam.getId());

        assertEquals(0, teamService.findTeamMembersByTeamId(findTeam.getId()).size());
        assertThrows(IllegalArgumentException.class, () -> teamService.findTeamByName("teamA"));
        assertThrows(IllegalArgumentException.class, () -> boardService.findById(addBoardResponse.getBoardId()));
        assertEquals(0, findTeam.getBoards().size());
        assertEquals(0, findMember.getPosts().size());
        assertThrows(NotExistDataException.class, () -> postService.findPost(post1.getPostId()));
        assertThrows(NotExistDataException.class, () -> postService.findPost(post2.getPostId()));
    }

    /**
     * teamA(memberA) pjh
     * teamC(PJH) kjh memberA
     * teamD(KJH) pjh
     */
    @Test
    public void ????????????_???_????????????()
    {
        MemberJoinRequest joinPJH = MemberJoinRequest.memberJoinRequestBuilder().id("pjh")
                .name("?????????").pwd("1234").nickname("??????????????????").address(new Address("??????", "?????????", "11"))
                .birth(LocalDate.of(2000, 1, 1)).email("fblpjh@naver.com").phone("010-1111-2222")
                .type(MemberType.ROLE_NORMAL).build();
        MemberJoinRequest joinKJH = MemberJoinRequest.memberJoinRequestBuilder().id("kjh")
                .name("?????????").pwd("1234").nickname("??????????????????").address(new Address("??????", "?????????", "11"))
                .birth(LocalDate.of(2000, 1, 1)).email("fblkjh@naver.com").phone("010-1111-2222")
                .type(MemberType.ROLE_NORMAL).build();
        QueryMemberDto findPJH = memberService.join(joinPJH);
        memberService.join(joinKJH);
        QueryTeamDto teamA = teamService.findTeamByName("teamA");
        CreateTeamResponse teamC = teamService.createNewTeam(joinPJH.getId(), "teamC","??? C ??????");
        CreateTeamResponse teamD = teamService.createNewTeam(joinKJH.getId(), "teamD","??? D ??????");

        teamService.applyToTeam(teamC.getTeamId(), "kjh", "hi");
        teamService.acceptApplication(teamC.getTeamId(), "kjh");

        teamService.applyToTeam(teamD.getTeamId(), "pjh", "hi");
        teamService.acceptApplication(teamD.getTeamId(), "pjh");

        teamService.applyToTeam(teamA.getId(), "pjh", "hi");
        teamService.acceptApplication(teamA.getId(), "pjh");

        teamService.applyToTeam(teamC.getTeamId(), "memberA", "hi");
        teamService.acceptApplication(teamC.getTeamId(), "memberA");

        log.info("########################### ?????? ?????? #############################");
        em.flush();
        em.clear();
        List<QueryTeamListItemDto> result = teamService.findAllTeamByMemberNumber(findPJH.getNumber());
        for (QueryTeamListItemDto item : result) {
            log.info("teamId={}, teamName={}, teamMemberTotal = {}, MyAuthority = {}",item.getTeamId(),item.getTeamName(),item.getTotalMemberCount(),item.getAuthority());
        }
    }

    /**
     * ????????? ?????? ????????? ?????? ???, ??? ????????? ??? ???????????? ????????? ??????
     */
    @Test
    public void ???_?????????_????????????_?????????()
    {
        QueryTeamDto findTeam = teamService.findTeamByName("teamA");
        Long teamId = findTeam.getId();
        String introduce = "????????? ??? ?????? ?????????.";

        teamService.updateTeamProfile(teamId, null, introduce);
        QueryTeamDto team = teamService.findTeam(findTeam.getId());
        Assertions.assertEquals(introduce, team.getIntroduce());
    }

}