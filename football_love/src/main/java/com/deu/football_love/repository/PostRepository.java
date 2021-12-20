package com.deu.football_love.repository;

import com.deu.football_love.domain.Post;

import java.util.List;

public interface PostRepository {
    void insertPost(Post newPost);

    void deletePost(Post post);

    Post selectPost(Long postId);

    List<Post> selectAllPostsByBoardId(Long boardId);
}
