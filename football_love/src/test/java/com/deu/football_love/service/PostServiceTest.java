package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.AddBoardResponse;
import com.deu.football_love.dto.board.BoardDto;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.MemberResponse;
import com.deu.football_love.dto.post.QueryPostDto;
import com.deu.football_love.dto.post.WritePostRequest;
import com.deu.football_love.dto.post.WritePostResponse;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.QueryTeamDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private BoardService boardService;

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void addPostTest()
    {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        MemberResponse memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");

        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);

        BoardDto findBoard = boardService.findById(response.getBoardId());

        WritePostRequest writePostRequest = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), "title", "hi");
        WritePostResponse writePostResponse = postService.writePost(writePostRequest);
        System.out.println("writePostResponse = " + writePostResponse);
        QueryPostDto findPost = postService.findPost(writePostResponse.getPostId());
        Assertions.assertNotNull(findPost);

    }

    @Test
    public void deletePostTest()
    {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        MemberResponse memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());
        WritePostRequest writePostRequest = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), "title", "hi");
        WritePostResponse writePostResponse = postService.writePost(writePostRequest);
        System.out.println("writePostResponse = " + writePostResponse);
        QueryPostDto findPost = postService.findPost(writePostResponse.getPostId());
        Assertions.assertNotNull(findPost);

        postService.deletePost(findPost.getId());

        Assertions.assertNull(postService.findPost(findPost.getId()));
    }

    @Test
    public void findAllPostByBoardIdTest()
    {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        MemberResponse memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());
        WritePostRequest writePostRequest1 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), "title1", "hi");
        WritePostRequest writePostRequest2 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), "title2", "hi");
        WritePostRequest writePostRequest3 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), "title3", "hi");
        WritePostRequest writePostRequest4 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), "title4", "hi");

        WritePostResponse writePostResponse1 = postService.writePost(writePostRequest1);
        WritePostResponse writePostResponse2 = postService.writePost(writePostRequest2);
        WritePostResponse writePostResponse3 = postService.writePost(writePostRequest3);
        WritePostResponse writePostResponse4 = postService.writePost(writePostRequest4);


        List<QueryPostDto> postList = postService.findAllPostsByBoardId(findBoard.getBoardId());

        for (QueryPostDto postDto : postList) {
            System.out.println("postDto = " + postDto);
        }

        Assertions.assertEquals(4, postList.size());
        Assertions.assertEquals(writePostRequest1.getTitle(), writePostResponse1.getTitle());
        Assertions.assertEquals(writePostRequest2.getTitle(), writePostResponse2.getTitle());
        Assertions.assertEquals(writePostRequest3.getTitle(), writePostResponse3.getTitle());
        Assertions.assertEquals(writePostRequest4.getTitle(), writePostResponse4.getTitle());
    }


    /**
     * board를 지우면 해당 게시판에 속한 post들이 모두 삭제 되는지 테스트
     */
    @Test
    public void deleteBoardCascadeTest()
    {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        MemberResponse memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());
        WritePostRequest writePostRequest1 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), "title1", "hi");
        WritePostRequest writePostRequest2 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), "title2", "hi");
        WritePostRequest writePostRequest3 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), "title3", "hi");
        WritePostRequest writePostRequest4 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), "title4", "hi");

        WritePostResponse writePostResponse1 = postService.writePost(writePostRequest1);
        WritePostResponse writePostResponse2 = postService.writePost(writePostRequest2);
        WritePostResponse writePostResponse3 = postService.writePost(writePostRequest3);
        WritePostResponse writePostResponse4 = postService.writePost(writePostRequest4);

        System.out.println("findBoard = " + findBoard.getBoardId());
        boardService.delete(findBoard.getBoardId());
        List<QueryPostDto> postList = postService.findAllPostsByBoardId(findBoard.getBoardId());

        BoardDto byId = boardService.findById(findBoard.getBoardId());

        Assertions.assertNull(byId);
        Assertions.assertEquals(0, postList.size());

    }
}
