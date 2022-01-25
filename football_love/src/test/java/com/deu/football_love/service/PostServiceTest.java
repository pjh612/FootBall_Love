package com.deu.football_love.service;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.AddBoardResponse;
import com.deu.football_love.dto.board.BoardDto;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.post.QueryPostDto;
import com.deu.football_love.dto.post.WritePostRequest;
import com.deu.football_love.dto.post.WritePostResponse;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.repository.BoardRepository;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.PostRepository;
import com.deu.football_love.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void addPostTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");

        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);

        BoardDto findBoard = boardService.findById(response.getBoardId());

        WritePostRequest writePostRequest = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title", "hi", null);
        WritePostResponse writePostResponse = postService.writePost(writePostRequest);
        System.out.println("writePostResponse = " + writePostResponse);
        QueryPostDto findPost = postService.findPost(writePostResponse.getPostId());
        Assertions.assertNotNull(findPost);

    }

    @Test
    public void deletePostTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());
        WritePostRequest writePostRequest = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title", "hi", null);
        WritePostResponse writePostResponse = postService.writePost(writePostRequest);
        System.out.println("writePostResponse = " + writePostResponse);
        QueryPostDto findPost = postService.findPost(writePostResponse.getPostId());
        Assertions.assertNotNull(findPost);

        postService.deletePost(findPost.getId());

        Assertions.assertNull(postService.findPost(findPost.getId()));
    }

    /**
     * Board가 삭제되면 게시글도 같이 삭제되는지 테스트
     */
    @Test
    public void cascadeBoardTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());
        WritePostRequest writePostRequest1 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);
        WritePostRequest writePostRequest2 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title2", "hi", null);
        WritePostRequest writePostRequest3 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title3", "hi", null);
        WritePostRequest writePostRequest4 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title4", "hi", null);

        WritePostResponse writePostResponse1 = postService.writePost(writePostRequest1);
        WritePostResponse writePostResponse2 = postService.writePost(writePostRequest2);
        WritePostResponse writePostResponse3 = postService.writePost(writePostRequest3);
        WritePostResponse writePostResponse4 = postService.writePost(writePostRequest4);

        Board board = boardRepository.selectBoardById(findBoard.getBoardId());
        while (board.getPosts().size() != 0) {
            Post curPost = board.getPosts().get(0);
            curPost.deletePost();
            postRepository.deletePost(curPost);
        }
        board.deleteBoard();

        boardRepository.deleteBoard(board);

        Assertions.assertNull(postRepository.selectPost(writePostResponse1.getPostId()));
        Assertions.assertNull(postRepository.selectPost(writePostResponse2.getPostId()));
        Assertions.assertNull(postRepository.selectPost(writePostResponse3.getPostId()));
        Assertions.assertNull(postRepository.selectPost(writePostResponse4.getPostId()));
    }

    /**
     * Member가 삭제되면 게시글도 같이 삭제되는지 테스트
     */
    @Test
    public void cascadeMemberTest() {
        //given
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());
        WritePostRequest writePostRequest1 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);
        WritePostRequest writePostRequest2 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title2", "hi", null);
        WritePostRequest writePostRequest3 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title3", "hi", null);
        WritePostRequest writePostRequest4 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title4", "hi", null);

        WritePostResponse writePostResponse1 = postService.writePost(writePostRequest1);
        WritePostResponse writePostResponse2 = postService.writePost(writePostRequest2);
        WritePostResponse writePostResponse3 = postService.writePost(writePostRequest3);
        WritePostResponse writePostResponse4 = postService.writePost(writePostRequest4);

        Member findMember = memberRepository.selectMemberById("memberA");

        //when
        memberService.withdraw(findMember.getId());

        //then
        Assertions.assertNull(postRepository.selectPost(writePostResponse1.getPostId()));
        Assertions.assertNull(postRepository.selectPost(writePostResponse2.getPostId()));
        Assertions.assertNull(postRepository.selectPost(writePostResponse3.getPostId()));
        Assertions.assertNull(postRepository.selectPost(writePostResponse4.getPostId()));
    }


    @Test
    public void findAllPostByBoardIdTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());
        WritePostRequest writePostRequest1 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);
        WritePostRequest writePostRequest2 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title2", "hi", null);
        WritePostRequest writePostRequest3 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title3", "hi", null);
        WritePostRequest writePostRequest4 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title4", "hi", null);

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
    public void deleteBoardCascadeTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());
        WritePostRequest writePostRequest1 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);
        WritePostRequest writePostRequest2 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title2", "hi", null);
        WritePostRequest writePostRequest3 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title3", "hi", null);
        WritePostRequest writePostRequest4 = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title4", "hi", null);

        postService.writePost(writePostRequest1);
        postService.writePost(writePostRequest2);
        postService.writePost(writePostRequest3);
        postService.writePost(writePostRequest4);

        boardService.delete(findBoard.getBoardId());
        List<QueryPostDto> postList = postService.findAllPostsByBoardId(findBoard.getBoardId());

        BoardDto byId = boardService.findById(findBoard.getBoardId());

        Assertions.assertNull(byId);
        Assertions.assertEquals(0, postList.size());

    }

    @Test
    void findPost() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());
        WritePostRequest writePostRequest = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);

        WritePostResponse writePostResponse = postService.writePost(writePostRequest);


        QueryPostDto findPost = postService.findPost(writePostResponse.getPostId());

        Assertions.assertEquals(writePostRequest.getTitle(), findPost.getTitle());
        Assertions.assertEquals(writePostRequest.getBoardId(), findPost.getBoardId());
        Assertions.assertEquals(writePostRequest.getContent(), findPost.getContent());
        Assertions.assertEquals(writePostRequest.getTeamId(), findPost.getTeamId());
        Assertions.assertEquals(writePostRequest.getAuthorNumber(), findPost.getAuthorNumber());
        Assertions.assertEquals(writePostResponse.getPostId(), findPost.getId());
    }

    @Test
    void findAllPostsByMemberId() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        CreateTeamResponse teamB = teamService.createNewTeam(memberADto.getId(), "teamB");
        QueryTeamDto findTeamA = teamService.findTeam(teamA.getTeamId());
        QueryTeamDto findTeamB = teamService.findTeam(teamB.getTeamId());

        Assertions.assertNotNull(findTeamA);
        Assertions.assertNotNull(findTeamB);

        AddBoardRequest addBoardRequestA = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardRequest addBoardRequestB = new AddBoardRequest("boardB", BoardType.NOTICE, teamB.getTeamId());
        AddBoardResponse addBoardResponseA = boardService.add(addBoardRequestA);
        AddBoardResponse addBoardResponseB = boardService.add(addBoardRequestB);

        BoardDto findBoardA = boardService.findById(addBoardResponseA.getBoardId());
        BoardDto findBoardB = boardService.findById(addBoardResponseB.getBoardId());

        WritePostRequest writePostRequestA = new WritePostRequest(memberJoinResponse.getNumber(), findBoardA.getBoardId(), teamA.getTeamId(), "title1", "this is teamA board A post", null);
        WritePostRequest writePostRequestB = new WritePostRequest(memberJoinResponse.getNumber(), findBoardB.getBoardId(), teamB.getTeamId(), "title2", "this is teamB board B post", null);

        postService.writePost(writePostRequestA);
        postService.writePost(writePostRequestB);

        List<QueryPostDto> findPosts = postService.findAllPostsByMemberId(memberADto.getId());

        Assertions.assertEquals(2, findPosts.size());
        Assertions.assertEquals(writePostRequestA.getTitle(), findPosts.get(0).getTitle());
        Assertions.assertEquals(writePostRequestA.getContent(), findPosts.get(0).getContent());
        Assertions.assertEquals(writePostRequestA.getAuthorNumber(), findPosts.get(0).getAuthorNumber());
        Assertions.assertEquals(writePostRequestA.getTeamId(), findPosts.get(0).getTeamId());
        Assertions.assertEquals(writePostRequestA.getBoardId(), findPosts.get(0).getBoardId());
        Assertions.assertEquals(memberADto.getId(), findPosts.get(0).getAuthorId());

        Assertions.assertEquals(writePostRequestB.getTitle(), findPosts.get(1).getTitle());
        Assertions.assertEquals(writePostRequestB.getContent(), findPosts.get(1).getContent());
        Assertions.assertEquals(writePostRequestB.getAuthorNumber(), findPosts.get(1).getAuthorNumber());
        Assertions.assertEquals(writePostRequestB.getTeamId(), findPosts.get(1).getTeamId());
        Assertions.assertEquals(writePostRequestB.getBoardId(), findPosts.get(1).getBoardId());
        Assertions.assertEquals(memberADto.getId(), findPosts.get(1).getAuthorId());
    }
}
