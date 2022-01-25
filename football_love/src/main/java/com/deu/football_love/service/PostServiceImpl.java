package com.deu.football_love.service;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Post;
import com.deu.football_love.domain.PostImage;
import com.deu.football_love.dto.post.*;
import com.deu.football_love.repository.BoardRepository;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final GcpStorageService gcpStorageService;

    @SneakyThrows
    @Override

    public WritePostResponse writePost(WritePostRequest request) {
        Post newPost = new Post();
        Member findMember = memberRepository.selectMember(request.getAuthorNumber());
        Board findBoard = boardRepository.selectBoardById(request.getBoardId());
        newPost.setContent(request.getContent());
        newPost.setTitle(request.getTitle());
        newPost.setAuthor(findMember);
        newPost.setBoard(findBoard);
        findMember.getPosts().add(newPost);
        findBoard.getPosts().add(newPost);
        postRepository.insertPost(newPost);
        if(request.getImages() != null) {
            for (MultipartFile image : request.getImages()) {
                String imgUri = gcpStorageService.updatePostImg(image);
                PostImage postImage = newPost.addPostImage(imgUri);
                postRepository.insertPostImg(postImage);
            }
        }
        return WritePostResponse.from(newPost);
    }

    @Override
    public DeletePostResponse deletePost(Long postId) {
        Post post = postRepository.selectPost(postId);
        if (post == null)
            return null;
        postRepository.deletePost(post);
        return new DeletePostResponse(postId);
    }

    @Override
    public ModifyPostResponse modifyPost(Long postId, UpdatePostRequest request) {
        Post post = postRepository.selectPost(postId);
        if (post == null)
            return null;
        post.update(request);
        return new ModifyPostResponse(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public QueryPostDto findPost(Long postId) {
        QueryPostDto result = postRepository.selectPostByPostId(postId);
        if (result == null)
            return null;
        return result;
    }

    @Override
    public List<QueryPostDto> findAllPostsByBoardId(Long boardId) {
        List<QueryPostDto> posts = postRepository.selectAllPostsByBoardId(boardId)
                .stream()
                .map(p -> new QueryPostDto(p))
                .collect(Collectors.toList());
        for (QueryPostDto post : posts) {
            post.setPostImages(
                    postRepository.selectPostImagesByPostId(post.getId())
                    .stream()
                    .map(pi -> QueryPostImageDto.from(pi))
                    .collect(Collectors.toList())
            );
        }
        return posts;

    }

    @Override
    public List<QueryPostDto> findAllPostsByMemberId(String memberId) {
        List<QueryPostDto> posts = postRepository.selectPostsByMemberId(memberId)
                .stream()
                .map(p -> new QueryPostDto(p))
                .collect(Collectors.toList());
        for (QueryPostDto post : posts) {
            post.setPostImages(
                    postRepository.selectPostImagesByPostId(post.getId())
                            .stream()
                            .map(pi -> QueryPostImageDto.from(pi))
                            .collect(Collectors.toList())
            );
        }
        return posts;
    }
}
