package com.deu.football_love.repository;

import com.deu.football_love.domain.Post;
import com.deu.football_love.dto.post.QueryPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository{

    private final EntityManager em;

    @Override
    public void insertPost(Post newPost) {
        em.persist(newPost);
    }

    @Override
    public void deletePost(Post post) { em.remove(post);}

    @Override
    public Post selectPost(Long postId) { return em.find(Post.class, postId);}

    public List<Post> selectAllPostsByBoardId(Long boardId) {
        return em.createQuery("SELECT p FROM Post p WHERE p.board.id = :boardId")
                .setParameter("boardId", boardId)
                .getResultList();
    }

    List<String> selectPostImagesByPostId(Long postId)
    {
        return em.createQuery("SELECT pi FROM PostImage pi " +
                "JOIN FETCH pi.post p " +
                "WHERE p.id =:postId", String.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public QueryPostDto selectPostByPostId(Long postId)
    {
        List<QueryPostDto> result = em.createQuery("SELECT new com.deu.football_love.dto.post.QueryPostDto(p) " +
                "FROM Post p " +
                "WHERE p.id =:postId", QueryPostDto.class)
                .setParameter("postId", postId)
                .getResultList();
        if (result.size() == 0)
            return null;
        List<String> postImages = selectPostImagesByPostId(postId);
        result.get(0).setPostImages(postImages);
        return result.get(0);
    }
}
