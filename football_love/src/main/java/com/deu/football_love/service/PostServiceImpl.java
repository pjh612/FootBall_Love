package com.deu.football_love.service;

import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Post;
import com.deu.football_love.dto.*;
import com.deu.football_love.repository.BoardRepository;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    @Override

    @Transactional(readOnly = false)
    public WritePostResponse writePost(WritePostRequest request) {
        Post newPost = new Post();
        Member findMember = memberRepository.selectMember(request.getAuthor());
        newPost.setContent(request.getContent());
        newPost.setTitle(request.getTitle());
        newPost.setAuthor(findMember);
        newPost.setCreateDate(request.getCreateDate());
        newPost.setModifyDate(request.getModifyDate());
        /*newPost.setBoard(findBoard);*/
        postRepository.insertPost(newPost);
        return WritePostResponse.from(newPost);
    }

    @Override
    @Transactional(readOnly = false)
    public DeletePostResponse deletePost(Long postId) {
        Post post = postRepository.selectPost(postId);
        if (post == null)
            return null;
        postRepository.deletePost(post);
        return new DeletePostResponse(postId);
    }

    @Override
    @Transactional(readOnly = false)
    public ModifyPostResponse modifyPost(Long postId, UpdatePostRequest request)
    {
        Post post = postRepository.selectPost(postId);
        if (post == null)
            return null;
        post.update(request);
        return new ModifyPostResponse(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto findPost(Long postId) {
        Post post = postRepository.selectPost(postId);
        if (post == null)
            return null;
        return PostDto.from(post);
    }
}
