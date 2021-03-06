package com.deu.football_love.service;

import com.deu.football_love.exception.DuplicatedException;
import com.deu.football_love.exception.NotExistDataException;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Post;
import com.deu.football_love.domain.TeamBoard;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.Teamboard.AddTeamBoardRequest;
import com.deu.football_love.dto.Teamboard.AddTeamBoardResponse;
import com.deu.football_love.dto.Teamboard.TeamBoardDto;
import com.deu.football_love.dto.comment.AddCommentRequest;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.post.QueryPostDto;
import com.deu.football_love.dto.post.WritePostRequest;
import com.deu.football_love.dto.post.WritePostResponse;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.PostRepository;
import com.deu.football_love.repository.TeamBoardRepository;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class PostServiceTest {

  @Autowired
  private EntityManager em;

  @Autowired
  private TeamBoardService boardService;

  @Autowired
  private PostService postService;

  @Autowired
  private TeamService teamService;

  @Autowired
  private MemberService memberService;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private TeamBoardRepository boardRepository;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private CommentService commentService;

  @Test
  public void addPostTest() {
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "??? A ??????");

    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    AddTeamBoardRequest request = new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
    AddTeamBoardResponse response = boardService.add(request);

    TeamBoardDto findBoard = boardService.findById(response.getBoardId());

    WritePostRequest writePostRequest =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title", "hi", null);
    WritePostResponse writePostResponse = postService.writePost(writePostRequest,memberJoinResponse.getNumber());
    QueryPostDto findPost = postService.findPost(writePostResponse.getPostId());
    Assertions.assertNotNull(findPost);
  }

  @Test
  public void deletePostTest() {
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "??? A ??????");
    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    AddTeamBoardRequest request = new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
    AddTeamBoardResponse response = boardService.add(request);
    TeamBoardDto findBoard = boardService.findById(response.getBoardId());
    WritePostRequest writePostRequest =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title", "hi", null);
    WritePostResponse writePostResponse = postService.writePost(writePostRequest, memberJoinResponse.getNumber());

    QueryPostDto findPost = postService.findPost(writePostResponse.getPostId());
    Assertions.assertNotNull(findPost);

    postService.deletePost(findPost.getId());
    Assertions.assertThrows(NotExistDataException.class, () -> postService.findPost(writePostResponse.getPostId()));
  }

  /**
   * Board??? ???????????? ???????????? ?????? ??????????????? ?????????
   */
  @Test
  public void cascadeBoardTest() {
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "??? A ??????");
    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    AddTeamBoardRequest request = new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
    AddTeamBoardResponse response = boardService.add(request);
    TeamBoardDto findBoard = boardService.findById(response.getBoardId());
    WritePostRequest writePostRequest1 =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);
    WritePostRequest writePostRequest2 =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title2", "hi", null);
    WritePostRequest writePostRequest3 =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title3", "hi", null);
    WritePostRequest writePostRequest4 =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title4", "hi", null);

    WritePostResponse writePostResponse1 = postService.writePost(writePostRequest1, memberJoinResponse.getNumber());
    WritePostResponse writePostResponse2 = postService.writePost(writePostRequest2,memberJoinResponse.getNumber());
    WritePostResponse writePostResponse3 = postService.writePost(writePostRequest3,memberJoinResponse.getNumber());
    WritePostResponse writePostResponse4 = postService.writePost(writePostRequest4,memberJoinResponse.getNumber());

    TeamBoard board = boardRepository.findById(findBoard.getBoardId()).get();
    while (board.getPosts().size() != 0) {
      Post curPost = board.getPosts().get(0);
      curPost.deletePost();
      postRepository.delete(curPost);
    }
    board.deleteBoard();

    boardRepository.delete(board);

    Assertions.assertFalse(postRepository.findById(writePostResponse1.getPostId()).isPresent());
    Assertions.assertFalse(postRepository.findById(writePostResponse2.getPostId()).isPresent());
    Assertions.assertFalse(postRepository.findById(writePostResponse3.getPostId()).isPresent());
    Assertions.assertFalse(postRepository.findById(writePostResponse4.getPostId()).isPresent());
  }

  /**
   * Member??? ???????????? ???????????? ?????? ??????????????? ?????????
   */
  @Test
  public void cascadeMemberTest() {
    // given
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("memberA").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "??? A ??????");
    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    AddTeamBoardRequest request = new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
    AddTeamBoardResponse response = boardService.add(request);
    TeamBoardDto findBoard = boardService.findById(response.getBoardId());
    WritePostRequest writePostRequest1 =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);
    WritePostRequest writePostRequest2 =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title2", "hi", null);
    WritePostRequest writePostRequest3 =
        new WritePostRequest( findBoard.getBoardId(), teamA.getTeamId(), "title3", "hi", null);
    WritePostRequest writePostRequest4 =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title4", "hi", null);

    WritePostResponse writePostResponse1 = postService.writePost(writePostRequest1,memberJoinResponse.getNumber());
    WritePostResponse writePostResponse2 = postService.writePost(writePostRequest2,memberJoinResponse.getNumber());
    WritePostResponse writePostResponse3 = postService.writePost(writePostRequest3,memberJoinResponse.getNumber());
    WritePostResponse writePostResponse4 = postService.writePost(writePostRequest4,memberJoinResponse.getNumber());

    Member findMember = memberRepository.findById("memberA").get();

    // when
    memberService.withdraw(findMember.getId());

    // then
    Assertions.assertFalse(postRepository.findById(writePostResponse1.getPostId()).isPresent());
    Assertions.assertFalse(postRepository.findById(writePostResponse2.getPostId()).isPresent());
    Assertions.assertFalse(postRepository.findById(writePostResponse3.getPostId()).isPresent());
    Assertions.assertFalse(postRepository.findById(writePostResponse4.getPostId()).isPresent());
  }


  @Test
  public void findAllPostByBoardIdTest() {
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "??? A ??????");
    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    AddTeamBoardRequest request = new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
    AddTeamBoardResponse response = boardService.add(request);
    TeamBoardDto findBoard = boardService.findById(response.getBoardId());

    Long postId = 0L;
    for (int i = 0; i < 100; i++) {
      WritePostRequest writePostRequest = new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(),
          "title" + Integer.toString(i), "hi" + i, null);
      WritePostResponse writePostResponse = postService.writePost(writePostRequest,memberJoinResponse.getNumber());
      postId = writePostResponse.getPostId();
    }
    Page<QueryPostDto> postList = postService.findAllPostsByBoardId(findBoard.getBoardId(), null);
    Assertions.assertEquals(100, postList.getTotalElements());

    for (int i = 0; i < 5; i++) {
      postList = postService.findAllPostsByBoardId(findBoard.getBoardId(), PageRequest.of(i, 20));
      for (int j = 0; j < 20; j++) {
        List<QueryPostDto> content = postList.getContent();
        // log.info("like count = {}",content.get(j).getLikeCount());
        Assertions.assertEquals("title" + (i * 20 + j), content.get(j).getTitle());
        Assertions.assertEquals("hi" + (i * 20 + j), content.get(j).getContent());
      }
    }
  }


  /**
   * board??? ????????? ?????? ???????????? ?????? post?????? ?????? ?????? ????????? ?????????
   */
  @Test
  public void deleteBoardCascadeTest() {
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "??? A ??????");
    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    AddTeamBoardRequest request = new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
    AddTeamBoardResponse response = boardService.add(request);
    TeamBoardDto findBoard = boardService.findById(response.getBoardId());
    WritePostRequest writePostRequest1 =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);
    WritePostRequest writePostRequest2 =
        new WritePostRequest( findBoard.getBoardId(), teamA.getTeamId(), "title2", "hi", null);
    WritePostRequest writePostRequest3 =
        new WritePostRequest( findBoard.getBoardId(), teamA.getTeamId(), "title3", "hi", null);
    WritePostRequest writePostRequest4 =
        new WritePostRequest( findBoard.getBoardId(), teamA.getTeamId(), "title4", "hi", null);

    postService.writePost(writePostRequest1,memberJoinResponse.getNumber());
    postService.writePost(writePostRequest2,memberJoinResponse.getNumber());
    postService.writePost(writePostRequest3,memberJoinResponse.getNumber());
    postService.writePost(writePostRequest4,memberJoinResponse.getNumber());

    boardService.delete(findBoard.getBoardId());

    Page<QueryPostDto> postList = postService.findAllPostsByBoardId(findBoard.getBoardId(), null);

    Assertions.assertThrows(IllegalArgumentException.class, () -> boardService.findById(findBoard.getBoardId()));
    Assertions.assertEquals(0, postList.getTotalElements());

  }

  @Test
  void findPost() {
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "??? A ??????");
    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    AddTeamBoardRequest request = new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
    AddTeamBoardResponse response = boardService.add(request);
    TeamBoardDto findBoard = boardService.findById(response.getBoardId());
    WritePostRequest writePostRequest =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);

    WritePostResponse writePostResponse = postService.writePost(writePostRequest,memberJoinResponse.getNumber());


    QueryPostDto findPost = postService.findPost(writePostResponse.getPostId());

    Assertions.assertEquals(writePostRequest.getTitle(), findPost.getTitle());
    Assertions.assertEquals(writePostRequest.getBoardId(), findPost.getBoardId());
    Assertions.assertEquals(writePostRequest.getContent(), findPost.getContent());
    Assertions.assertEquals(memberJoinResponse.getNumber(), findPost.getAuthorNumber());
    Assertions.assertEquals(writePostResponse.getPostId(), findPost.getId());

  }

  /**
   * ?????? ????????? ??? ??????
   */
  @Test
  void findAllPostsByMemberId() {
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "??? A ??????");
    CreateTeamResponse teamB = teamService.createNewTeam(memberADto.getId(), "teamB", "??? B ??????");
    QueryTeamDto findTeamA = teamService.findTeam(teamA.getTeamId());
    QueryTeamDto findTeamB = teamService.findTeam(teamB.getTeamId());

    Assertions.assertNotNull(findTeamA);
    Assertions.assertNotNull(findTeamB);

    AddTeamBoardRequest addBoardRequestA = new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
    AddTeamBoardRequest addBoardRequestB = new AddTeamBoardRequest("boardB", BoardType.NOTICE, teamB.getTeamId());
    AddTeamBoardResponse addBoardResponseA = boardService.add(addBoardRequestA);
    AddTeamBoardResponse addBoardResponseB = boardService.add(addBoardRequestB);

    TeamBoardDto findBoardA = boardService.findById(addBoardResponseA.getBoardId());
    TeamBoardDto findBoardB = boardService.findById(addBoardResponseB.getBoardId());

    WritePostRequest writePostRequestA = new WritePostRequest( findBoardA.getBoardId(), teamA.getTeamId(), "title1",
        "this is teamA board A post", null);
    WritePostRequest writePostRequestB = new WritePostRequest( findBoardB.getBoardId(), teamB.getTeamId(), "title2",
        "this is teamB board B post", null);

    postService.writePost(writePostRequestA,memberJoinResponse.getNumber());
    postService.writePost(writePostRequestB,memberJoinResponse.getNumber());

    List<QueryPostDto> findPosts = postService.findAllPostsByMemberId(memberADto.getId());

    Assertions.assertEquals(2, findPosts.size());
    Assertions.assertEquals(writePostRequestA.getTitle(), findPosts.get(0).getTitle());
    Assertions.assertEquals(writePostRequestA.getContent(), findPosts.get(0).getContent());
    Assertions.assertEquals(memberJoinResponse.getNumber(), findPosts.get(0).getAuthorNumber());
    Assertions.assertEquals(writePostRequestA.getBoardId(), findPosts.get(0).getBoardId());
    Assertions.assertEquals(memberADto.getId(), findPosts.get(0).getAuthorId());

    Assertions.assertEquals(writePostRequestB.getTitle(), findPosts.get(1).getTitle());
    Assertions.assertEquals(writePostRequestB.getContent(), findPosts.get(1).getContent());
    Assertions.assertEquals(memberJoinResponse.getNumber(), findPosts.get(1).getAuthorNumber());
    Assertions.assertEquals(writePostRequestB.getBoardId(), findPosts.get(1).getBoardId());
    Assertions.assertEquals(memberADto.getId(), findPosts.get(1).getAuthorId());
  }

  /**
   * ????????? ?????? ??? ??????????????? ??????????????? ??????
   */
  @Test
  void findPostWithComment() {
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "??? A ??????");
    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    AddTeamBoardRequest request = new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId());
    AddTeamBoardResponse response = boardService.add(request);
    TeamBoardDto findBoard = boardService.findById(response.getBoardId());
    WritePostRequest writePostRequest =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);

    WritePostResponse writePostResponse = postService.writePost(writePostRequest,memberJoinResponse.getNumber());

    commentService.addComment(new AddCommentRequest(writePostResponse.getPostId(), memberJoinResponse.getNumber(), "?????? ?????????"));
    commentService.addComment(new AddCommentRequest(writePostResponse.getPostId(), memberJoinResponse.getNumber(), "?????? ?????????2"));

    QueryPostDto findPost = postService.findPost(writePostResponse.getPostId());

    Assertions.assertEquals(writePostRequest.getTitle(), findPost.getTitle());
    Assertions.assertEquals(writePostRequest.getBoardId(), findPost.getBoardId());
    Assertions.assertEquals(writePostRequest.getContent(), findPost.getContent());
    Assertions.assertEquals(memberJoinResponse.getNumber(), findPost.getAuthorNumber());
    Assertions.assertEquals(writePostResponse.getPostId(), findPost.getId());
    Assertions.assertEquals(0, findPost.getLikeCount());
    Assertions.assertEquals(2, findPost.getComments().size());
    Assertions.assertEquals("?????? ?????????", findPost.getComments().get(0).getComment());
    Assertions.assertEquals("?????? ?????????2", findPost.getComments().get(1).getComment());
  }

  @Test
  public void likePostTest() {
    // given
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "??? A ??????");
    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    TeamBoardDto findBoard =
        boardService.findById(boardService.add(new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId())).getBoardId());
    WritePostRequest writePostRequest =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);
    WritePostResponse writePostResponse = postService.writePost(writePostRequest,memberJoinResponse.getNumber());

    // when
    postService.likePost(writePostResponse.getPostId(), memberJoinResponse.getNumber());

    // then
    Assertions.assertEquals(1, postService.findPost(writePostResponse.getPostId()).getLikeCount());
  }

  @Test
  public void likeDoublePostTest() {
    // given
    MemberJoinRequest memberADto =
        MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1").name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
            .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberJoinResponse = memberService.join(memberADto);
    CreateTeamResponse teamA = teamService.createNewTeam(memberADto.getId(), "teamA", "??? A ??????");
    QueryTeamDto findTeam = teamService.findTeam(teamA.getTeamId());
    Assertions.assertNotNull(findTeam);
    TeamBoardDto findBoard =
        boardService.findById(boardService.add(new AddTeamBoardRequest("boardA", BoardType.NOTICE, teamA.getTeamId())).getBoardId());
    WritePostRequest writePostRequest =
        new WritePostRequest(findBoard.getBoardId(), teamA.getTeamId(), "title1", "hi", null);
    WritePostResponse writePostResponse = postService.writePost(writePostRequest,memberJoinResponse.getNumber());

    // when
    postService.likePost(writePostResponse.getPostId(), memberJoinResponse.getNumber());

    // then
    Assertions.assertThrows(DuplicatedException.class, () -> postService.likePost(writePostResponse.getPostId(), memberJoinResponse.getNumber()));


  }
}
