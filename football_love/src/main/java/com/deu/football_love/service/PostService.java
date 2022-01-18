package com.deu.football_love.service;

import com.deu.football_love.dto.post.*;

import java.util.List;

public interface PostService {
    WritePostResponse writePost(WritePostRequest request);

    DeletePostResponse deletePost(Long postId);

    ModifyPostResponse modifyPost(Long postId, UpdatePostRequest request);

    QueryPostDto findPost(Long postId);

    List<QueryPostDto> findAllPostsByBoardId(Long boardId);


}
