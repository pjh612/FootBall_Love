package com.deu.football_love.repository;

import com.deu.football_love.domain.Post;

public interface PostRepository {
    void insertPost(Post newPost);

    void deletePost(Post post);

    Post selectPost(Long postId);
}
