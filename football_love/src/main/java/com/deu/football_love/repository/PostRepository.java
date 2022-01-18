package com.deu.football_love.repository;

import com.deu.football_love.domain.Post;
import com.deu.football_love.dto.post.QueryPostDto;

import java.util.List;

public interface PostRepository {
    void insertPost(Post newPost);

    void deletePost(Post post);

    Post selectPost(Long postId);

    List<Post> selectAllPostsByBoardId(Long boardId);

    QueryPostDto selectPostByPostId(Long postId);
}
