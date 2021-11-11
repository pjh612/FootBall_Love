package com.deu.football_love.repository;

import com.deu.football_love.domain.Post;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
}
