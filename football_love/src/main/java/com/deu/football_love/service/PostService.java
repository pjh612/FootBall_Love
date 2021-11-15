package com.deu.football_love.service;

import com.deu.football_love.dto.*;

public interface PostService {
    WritePostResponse writePost(WritePostRequest request);
    DeletePostResponse deletePost(Long postId);
    ModifyPostResponse modifyPost(Long postId, UpdatePostRequest request);
    PostDto findPost(Long postId);
}
