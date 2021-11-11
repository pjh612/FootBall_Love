package com.deu.football_love.service;

import com.deu.football_love.domain.Post;
import com.deu.football_love.dto.DeletePostRequest;
import com.deu.football_love.dto.UpdatePostRequest;
import com.deu.football_love.dto.WritePostResponse;
import com.deu.football_love.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    @Transactional
    public WritePostResponse writePost(Post post) {
        postRepository.insertPost(post);
        return WritePostResponse.from(post);
    }

    @Override
    @Transactional
    public void deletePost(Post post) {
        postRepository.insertPost(post);
    }

    @Override
    @Transactional
    public void modifyPost(Post post, UpdatePostRequest request)
    {
        post.update(request);
    }

    @Override
    @Transactional
    public Post findPost(Long postId) {
        return postRepository.selectPost(postId);
    }
}
