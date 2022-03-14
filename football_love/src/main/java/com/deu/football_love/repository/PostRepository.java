package com.deu.football_love.repository;

import com.deu.football_love.domain.Post;
import com.deu.football_love.domain.PostImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  @Query(value = "SELECT p FROM Post p" +
      " JOIN FETCH p.board" +
      " WHERE p.board.id = :boardId",
      countQuery = "SELECT count(p) FROM Post p WHERE p.board.id = :boardId")
  Page<Post> selectAllPostsByBoardId(@Param("boardId") Long boardId, Pageable pageable);

  @Query(value = "SELECT p FROM Post p" +
      " JOIN FETCH p.board" +
      " WHERE p.board.id = :boardId",
      countQuery = "SELECT count(p) FROM Post p WHERE p.board.id = :boardId")
  Page<Post> selectAllPostsDtoByBoardId(@Param("boardId") Long boardId, Pageable pageable);


  @Query("SELECT p " +
      "FROM Post p " +
      "JOIN FETCH p.board " +
      "WHERE p.id =:postId")
  Optional<Post> selectPostByPostId(@Param("postId") Long postId);

  default Post selectPostByPostIdWrapper(Long postId) {
    Post post = selectPostByPostId(postId).orElseThrow(() -> new IllegalArgumentException("no such post data"));
    return post;
  }

  @Query("SELECT p " +
      "FROM Post p " +
      "JOIN FETCH p.author a " +
      "where a.id =:memberId")
  List<Post> selectPostsByMemberId(@Param("memberId") String memberId);

  @Query("SELECT pi FROM PostImage pi " +
      "JOIN FETCH pi.post p " +
      "WHERE p.id =:postId")
  List<PostImage> selectPostImagesByPostId(@Param("postId") Long postId);

  default Post findByIdWrapper(Long postId) {
    return this.findById(postId).orElseThrow(() -> new IllegalArgumentException("no such post data"));
  }

}
