package com.deu.football_love.service;

import com.deu.football_love.domain.Post;
import com.deu.football_love.dto.UpdatePostRequest;
import com.deu.football_love.dto.WritePostResponse;
import org.springframework.transaction.annotation.Transactional;

public interface PostService {
    WritePostResponse writePost(Post post);
    void deletePost(Post post);

    @Transactional
    void modifyPost(Post post, UpdatePostRequest request);

    @Transactional
    Post findPost(Long postId);
}
