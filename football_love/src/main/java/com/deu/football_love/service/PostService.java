package com.deu.football_love.service;

import com.deu.football_love.dto.post.*;

import java.util.List;

public interface PostService {
    WritePostResponse writePost(WritePostRequest request);

    DeletePostResponse deletePost(Long postId);

    public boolean isLiked(Long postId, Long memberNumber) {
        return postLikeRepository.existsByMemberNumberAndPostId(memberNumber, postId);
    }

    public LikePostResponse likePost(Long postId, Long memberNumber) {
        Post findPost = postRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException());
        Member findMember = memberRepository.selectMember(memberNumber);
        if (findMember == null)
            throw new IllegalArgumentException("no such data");
        if(isLiked(postId, memberNumber))
            throw new LikeDuplicatedException("이미 추천한 게시글 입니다.");
        PostLike like = new PostLike(findPost, findMember);
        postLikeRepository.save(like);
        return new LikePostResponse(true,"추천");
    }

    List<QueryPostDto> findAllPostsByBoardId(Long boardId);

    List<QueryPostDto> findAllPostsByMemberId(String memberId);
}
