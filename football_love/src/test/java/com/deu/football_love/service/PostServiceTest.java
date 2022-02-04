package com.deu.football_love.service;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.AddBoardResponse;
import com.deu.football_love.dto.board.BoardDto;
import com.deu.football_love.dto.comment.AddCommentRequest;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.post.QueryPostDto;
import com.deu.football_love.dto.post.WritePostRequest;
import com.deu.football_love.dto.post.WritePostResponse;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.exception.LikeDuplicatedException;
import com.deu.football_love.repository.BoardRepository;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.PostRepository;
import com.deu.football_love.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private PostService postService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentService commentService;

    @Test
    public void addPostTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", "1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");

        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);

        BoardDto findBoard = boardService.findById(response.getBoardId());

        WritePostRequest writePostRequest = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title", "hi", null);
        WritePostResponse writePostResponse = postService.writePost(writePostRequest);
        QueryPostDto findPost = postService.findPost(writePostResponse.getPostId());
        Assertions.assertNotNull(findPost);
    }

    @Test
    public void deletePostTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", "1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "010-1234-1234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());
        WritePostRequest writePostRequest = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title", "hi", null);
        WritePostResponse writePostResponse = postService.writePost(writePostRequest);

        QueryPostDto findPost = postService.findPost(writePostResponse.getPostId());
        Assertions.assertNotNull(findPost);

        postService.deletePost(findPost.getId());
        Assertions.assertThrows(IllegalArgumentException.class,()->postService.findPost(writePostResponse.getPostId()));
    }

    /**
     * Board가 삭제되면 게시글도 같이 삭제되는지 테스트
     */
    @Test
    public void cascadeBoardTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", "1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
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
            postRepository.delete(curPost);
        }
        board.deleteBoard();

        boardRepository.deleteBoard(board);

        Assertions.assertFalse(postRepository.findById(writePostResponse1.getPostId()).isPresent());
        Assertions.assertFalse(postRepository.findById(writePostResponse2.getPostId()).isPresent());
        Assertions.assertFalse(postRepository.findById(writePostResponse3.getPostId()).isPresent());
        Assertions.assertFalse(postRepository.findById(writePostResponse4.getPostId()).isPresent());
    }

    /**
     * Member가 삭제되면 게시글도 같이 삭제되는지 테스트
     */
    @Test
    public void cascadeMemberTest() {
        //given
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", "1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
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
        Assertions.assertFalse(postRepository.findById(writePostResponse1.getPostId()).isPresent());
        Assertions.assertFalse(postRepository.findById(writePostResponse2.getPostId()).isPresent());
        Assertions.assertFalse(postRepository.findById(writePostResponse3.getPostId()).isPresent());
        Assertions.assertFalse(postRepository.findById(writePostResponse4.getPostId()).isPresent());
    }


    @Test
    public void findAllPostByBoardIdTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", "1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());

        Long postId =0L;
        for(int i=0;i<100;i++)
        {
            WritePostRequest writePostRequest = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title"+Integer.toString(i), "hi"+i, null);
            WritePostResponse writePostResponse = postService.writePost(writePostRequest);
            postId = writePostResponse.getPostId();
        }
        Page<QueryPostDto> postList = postService.findAllPostsByBoardId(findBoard.getBoardId(), null);
        Assertions.assertEquals(100, postList.getTotalElements());

        for(int i=0;i<5;i++) {
            postList = postService.findAllPostsByBoardId(findBoard.getBoardId(), PageRequest.of(i, 20));
            for(int j=0;j<20;j++) {
                List<QueryPostDto> content = postList.getContent();
                //log.info("like count = {}",content.get(j).getLikeCount());
                Assertions.assertEquals("title" + (i*20 + j), content.get(j).getTitle());
                Assertions.assertEquals("hi" + (i*20 + j), content.get(j).getContent());
            }
        }
    }


    /**
     * board를 지우면 해당 게시판에 속한 post들이 모두 삭제 되는지 테스트
     */
    @Test
    public void deleteBoardCascadeTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", "1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
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

        Page<QueryPostDto> postList = postService.findAllPostsByBoardId(findBoard.getBoardId(),null);

        BoardDto byId = boardService.findById(findBoard.getBoardId());

        Assertions.assertNull(byId);
        Assertions.assertEquals(0, postList.getTotalElements());

    }

    @Test
    void findPost() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA","1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
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
        Assertions.assertEquals(writePostRequest.getAuthorNumber(), findPost.getAuthorNumber());
        Assertions.assertEquals(writePostResponse.getPostId(), findPost.getId());

    }

    /**
     * 특정 회원의 글 조회
     */
    @Test
    void findAllPostsByMemberId() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA","1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
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
        Assertions.assertEquals(writePostRequestA.getBoardId(), findPosts.get(0).getBoardId());
        Assertions.assertEquals(memberADto.getId(), findPosts.get(0).getAuthorId());

        Assertions.assertEquals(writePostRequestB.getTitle(), findPosts.get(1).getTitle());
        Assertions.assertEquals(writePostRequestB.getContent(), findPosts.get(1).getContent());
        Assertions.assertEquals(writePostRequestB.getAuthorNumber(), findPosts.get(1).getAuthorNumber());
        Assertions.assertEquals(writePostRequestB.getBoardId(), findPosts.get(1).getBoardId());
        Assertions.assertEquals(memberADto.getId(), findPosts.get(1).getAuthorId());
    }

    /**
     * 게시물 조회 시 코멘트까지 조회하는지 확인
     */
    @Test
    void findPostWithComment() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA","1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        AddBoardRequest request = new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
        AddBoardResponse response = boardService.add(request);
        BoardDto findBoard = boardService.findById(response.getBoardId());
        WritePostRequest writePostRequest = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);

        WritePostResponse writePostResponse = postService.writePost(writePostRequest);

        commentService.addComment(new AddCommentRequest(writePostResponse.getPostId(), memberJoinResponse.getNumber(), "댓글 테스트"));
        commentService.addComment(new AddCommentRequest(writePostResponse.getPostId(), memberJoinResponse.getNumber(), "댓글 테스트2"));

        QueryPostDto findPost = postService.findPost(writePostResponse.getPostId());

        Assertions.assertEquals(writePostRequest.getTitle(), findPost.getTitle());
        Assertions.assertEquals(writePostRequest.getBoardId(), findPost.getBoardId());
        Assertions.assertEquals(writePostRequest.getContent(), findPost.getContent());
        Assertions.assertEquals(writePostRequest.getAuthorNumber(), findPost.getAuthorNumber());
        Assertions.assertEquals(writePostResponse.getPostId(), findPost.getId());
        Assertions.assertEquals(0, findPost.getLikeCount());
        Assertions.assertEquals(2, findPost.getComments().size());
        Assertions.assertEquals("댓글 테스트", findPost.getComments().get(0).getComment());
        Assertions.assertEquals("댓글 테스트2", findPost.getComments().get(1).getComment());
    }

    @Test
    public void likePostTest() {
        //given
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA","1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        BoardDto findBoard = boardService.findById(boardService.add(new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId())).getBoardId());
        WritePostRequest writePostRequest = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);
        WritePostResponse writePostResponse = postService.writePost(writePostRequest);

        //when
        postService.likePost(writePostResponse.getPostId(), memberJoinResponse.getNumber());

        //then
        Assertions.assertEquals(1, postService.findPost(writePostResponse.getPostId()).getLikeCount());
    }

    @Test
    public void likeDoublePostTest() {
        //given
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA","1234", "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberJoinResponse = memberService.join(memberADto);
        CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA");
        QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
        Assertions.assertNotNull(findTeam);
        BoardDto findBoard = boardService.findById(boardService.add(new AddBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId())).getBoardId());
        WritePostRequest writePostRequest = new WritePostRequest(memberJoinResponse.getNumber(), findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);
        WritePostResponse writePostResponse = postService.writePost(writePostRequest);

        //when
        postService.likePost(writePostResponse.getPostId(), memberJoinResponse.getNumber());

        //then
        Assertions.assertThrows(LikeDuplicatedException.class , ()->postService.likePost(writePostResponse.getPostId(), memberJoinResponse.getNumber()));


    }
}
