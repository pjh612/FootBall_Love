package com.deu.football_love.repository;

import com.deu.football_love.domain.Post;
import com.deu.football_love.domain.PostImage;
import com.deu.football_love.dto.post.QueryPostDto;
import com.deu.football_love.dto.post.QueryPostImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final EntityManager em;

    @Override
    public void insertPost(Post newPost) {
        em.persist(newPost);
    }

    @Override
    public void insertPostImg(PostImage newPostImg) {
        em.persist(newPostImg);
    }


    @Override
    public void deletePost(Post post) {
        em.remove(post);
    }

    @Override
    public Post selectPost(Long postId) {
        return em.find(Post.class, postId);
    }

    public List<Post> selectAllPostsByBoardId(Long boardId) {
        return em.createQuery(
                "SELECT p FROM Post p" +
                        " JOIN FETCH p.board" +
                        " JOIN FETCH p.board.team" +
                        " WHERE p.board.id = :boardId"
        )
                .setParameter("boardId", boardId)
                .getResultList();
    }

    @Override
    public List<PostImage> selectPostImagesByPostId(Long postId) {
        return em.createQuery("SELECT pi FROM PostImage pi " +
                "JOIN FETCH pi.post p " +
                "WHERE p.id =:postId", PostImage.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public QueryPostDto selectPostByPostId(Long postId) {
        List<QueryPostDto> result = em.createQuery("SELECT p " +
                "FROM Post p " +
                "JOIN FETCH p.board " +
                "JOIN p.board.team " +
                "WHERE p.id =:postId", Post.class)
                .setParameter("postId", postId)
                .getResultList().stream()
                .map(p -> QueryPostDto.from(p))
                .collect(Collectors.toList());
        if (result.size() == 0)
            return null;
        List<QueryPostImageDto> postImages = selectPostImagesByPostId(postId)
                .stream()
                .map(pi -> QueryPostImageDto.from(pi))
                .collect(Collectors.toList());
        result.get(0).setPostImages(postImages);
        return result.get(0);
    }

    public List<Post> selectPostsByMemberId(String memberId) {
        return em.createQuery("SELECT p " +
                "FROM Post p " +
                "JOIN FETCH p.author a " +
                "where a.id =:memberId", Post.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
