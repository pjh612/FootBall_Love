package com.deu.football_love.repository;

import com.deu.football_love.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c" +
            " FROM Comment c" +
            " JOIN FETCH c.post" +
            " JOIN FETCH c.writer" +
            " WHERE c.post.id=:postId")
    List<Comment> selectCommentsByPostId(@Param("postId") Long postId);

    default Comment findByIdWrapper(Long commentId) {
        return this.findById(commentId).orElseThrow(()->new IllegalArgumentException("no such comment data"));
    }

}
