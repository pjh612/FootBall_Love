package com.deu.football_love.service;

import com.deu.football_love.exception.DuplicatedException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.deu.football_love.domain.TeamBoard;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Post;
import com.deu.football_love.domain.PostImage;
import com.deu.football_love.domain.PostLike;
import com.deu.football_love.dto.post.DeletePostResponse;
import com.deu.football_love.dto.post.ModifyPostResponse;
import com.deu.football_love.dto.post.QueryPostDto;
import com.deu.football_love.dto.post.QueryPostImageDto;
import com.deu.football_love.dto.post.UpdatePostRequest;
import com.deu.football_love.dto.post.WritePostRequest;
import com.deu.football_love.dto.post.WritePostResponse;
import com.deu.football_love.dto.post.like.LikePostResponse;
import com.deu.football_love.repository.TeamBoardRepository;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.PostImageRepository;
import com.deu.football_love.repository.PostLikeRepository;
import com.deu.football_love.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostService {

  private final PostRepository postRepository;
  private final PostLikeRepository postLikeRepository;
  private final PostImageRepository postImageRepository;
  private final TeamBoardRepository boardRepository;
  private final MemberRepository memberRepository;
  private final GcpStorageService gcpStorageService;

  @SneakyThrows
  public WritePostResponse writePost(WritePostRequest request) {
    Post newPost = new Post();
    Member findMember = memberRepository.findById(request.getAuthorNumber())
        .orElseThrow(() -> new IllegalArgumentException());
    TeamBoard findBoard = boardRepository.findById(request.getBoardId())
        .orElseThrow(() -> new IllegalArgumentException("no such board data."));
    newPost.setContent(request.getContent());
    newPost.setTitle(request.getTitle());
    newPost.setAuthor(findMember);
    newPost.setBoard(findBoard);
    findMember.getPosts().add(newPost);
    findBoard.getPosts().add(newPost);
    postRepository.save(newPost);
    if (request.getImages() != null) {
      for (MultipartFile image : request.getImages()) {
        String imgUri = gcpStorageService.updatePostImg(image);
        PostImage postImage = newPost.addPostImage(imgUri);
        postImageRepository.save(postImage);
      }
    }
    return WritePostResponse.from(newPost);
  }

  public DeletePostResponse deletePost(Long postId) {
    Post findPost =
        postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException());
    postRepository.delete(findPost);
    return new DeletePostResponse(postId);
  }

  public ModifyPostResponse modifyPost(Long postId, UpdatePostRequest request) {
    Post findPost =
        postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException());
    findPost.update(request);
    return new ModifyPostResponse(postId);
  }

  public boolean isLiked(Long postId, Long memberNumber) {
    return postLikeRepository.existsByMemberNumberAndPostId(memberNumber, postId);
  }

  public LikePostResponse likePost(Long postId, Long memberNumber) {
    Post findPost =
        postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException());
    Member findMember = memberRepository.findById(memberNumber)
        .orElseThrow(() -> new IllegalArgumentException("no such member data."));
    if (findMember == null)
      throw new IllegalArgumentException("no such data");
    if (isLiked(postId, memberNumber))
      throw new DuplicatedException("이미 추천한 게시글 입니다.");
    PostLike like = new PostLike(findPost, findMember);
    postLikeRepository.save(like);
    return new LikePostResponse(true, "추천");
  }

  public QueryPostDto findPost(Long postId) {
    Post findPost =
        postRepository.selectPostByPostId(postId).orElseThrow(() -> new IllegalArgumentException());
    QueryPostDto findPostDto = QueryPostDto.from(findPost);
    List<QueryPostImageDto> postImages = postRepository.selectPostImagesByPostId(postId).stream()
        .map(pi -> QueryPostImageDto.from(pi)).collect(Collectors.toList());
    findPostDto.setPostImages(postImages);
    findPostDto.setLikeCount(postLikeRepository.countByPostId(postId));
    return findPostDto;
  }

  public Page<QueryPostDto> findAllPostsByBoardId(Long boardId, Pageable pageable) {
    Page<Post> postPageList = postRepository.selectAllPostsByBoardId(boardId, pageable);
    Page<QueryPostDto> posts = postPageList.map(p -> QueryPostDto.from(p));
    for (QueryPostDto post : posts) {
      post.setPostImages(postRepository.selectPostImagesByPostId(post.getId()).stream()
          .map(pi -> QueryPostImageDto.from(pi)).collect(Collectors.toList()));
      post.setLikeCount(postLikeRepository.countByPostId(post.getId()));
    }
    return posts;
  }

  public List<QueryPostDto> findAllPostsByMemberId(String memberId) {
    List<QueryPostDto> posts = postRepository.selectPostsByMemberId(memberId).stream()
        .map(p -> QueryPostDto.from(p)).collect(Collectors.toList());
    for (QueryPostDto post : posts) {
      post.setPostImages(postRepository.selectPostImagesByPostId(post.getId()).stream()
          .map(pi -> QueryPostImageDto.from(pi)).collect(Collectors.toList()));
      post.setLikeCount(postLikeRepository.countByPostId(post.getId()));
    }
    return posts;
  }
}
