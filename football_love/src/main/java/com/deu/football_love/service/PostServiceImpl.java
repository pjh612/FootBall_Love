package com.deu.football_love.service;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Post;
import com.deu.football_love.dto.post.*;
import com.deu.football_love.repository.BoardRepository;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override

    public WritePostResponse writePost(WritePostRequest request) {
        Post newPost = new Post();
        Member findMember = memberRepository.selectMember(request.getAuthorNumber());
        Board findBoard = boardRepository.selectBoardById(request.getBoardId());
        newPost.setContent(request.getContent());
        newPost.setTitle(request.getTitle());
        newPost.setAuthor(findMember);
        newPost.setBoard(findBoard);
        findBoard.getPosts().add(newPost);
        postRepository.insertPost(newPost);
        return WritePostResponse.from(newPost);
    }

    @Override
    public DeletePostResponse deletePost(Long postId) {
        Post post = postRepository.selectPost(postId);
        if (post == null)
            return null;
        System.out.println("post = " + post);
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
        Post post = postRepository.selectPost(postId);
        if (post == null)
            return null;
        return QueryPostDto.from(post);
    }

    public List<QueryPostDto> findAllPostsByBoardId(Long boardId)
    {
        List<Post> posts = postRepository.selectAllPostsByBoardId(boardId);
        return posts.stream().map(p -> new QueryPostDto(p.getId(), p.getAuthor().getNumber(), p.getAuthor().getId(), p.getBoard().getId(), p.getCreatedDate(), p.getLastModifiedDate(), p.getTitle(), p.getContent())).collect(Collectors.toList());

    }
}
