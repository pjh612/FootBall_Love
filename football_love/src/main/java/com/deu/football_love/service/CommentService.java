package com.deu.football_love.service;

import com.deu.football_love.domain.Comment;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Post;
import com.deu.football_love.dto.comment.*;
import com.deu.football_love.repository.CommentRepository;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    public AddCommentResponse addComment(AddCommentRequest request) {
        Post findPost = postRepository.findById(request.getPostId()).orElseThrow(()->new IllegalArgumentException());
        Member findMember = memberRepository.selectMember(request.getWriterNumber());
        if (findMember == null)
            return new AddCommentResponse(false, null);
        Comment comment = new Comment(findPost, findMember, request.getComment());
        commentRepository.save(comment);
        return new AddCommentResponse(true, comment.getId());
    }


    public QueryCommentDto findCommentByCommentId(Long commentId) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException());
        return QueryCommentDto.from(findComment);
    }

    public List<QueryCommentDto> findCommentsByPostId(Long postId) {
        return commentRepository.selectCommentsByPostId(postId).stream().map(c -> QueryCommentDto.from(c)).collect(Collectors.toList());
    }

    public void updateCommentByCommentId(UpdateCommentRequest request) {
        Comment findComment = commentRepository.findById(request.getCommentId()).orElseThrow(()-> new IllegalArgumentException());
        findComment.updateComment(request);

    }

    public void deleteCommentByCommentId(Long commentId) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException());
        commentRepository.delete(findComment);
    }
}
