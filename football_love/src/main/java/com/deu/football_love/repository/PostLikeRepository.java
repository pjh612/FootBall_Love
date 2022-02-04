package com.deu.football_love.repository;

import com.deu.football_love.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findPostLikeByMemberNumberAndPostId(Long memberNumber, Long postId);

    boolean existsByMemberNumberAndPostId(Long memberNumber, Long postId);

    @Query("SELECT count(pl)" +
            " FROM PostLike pl" +
            " JOIN Post p ON p.id =:postId"
            )
    Long countByPostId(@Param("postId") Long postId);

    default PostLike findPostLikeByMemberNumberAndPostIdWrapper(Long memberNumber, Long postId)
    {
        return this.findPostLikeByMemberNumberAndPostId(memberNumber,postId).orElseThrow(()->new IllegalArgumentException("no such like data"));
    }
}
