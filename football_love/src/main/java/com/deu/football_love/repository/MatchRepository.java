package com.deu.football_love.repository;

import com.deu.football_love.domain.Matches;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MatchRepository {

    private final EntityManager em;

    public Matches selectMatch(Long id)
    {
        return em.find(Matches.class, id);
    }

    public void insertMatch(Matches match)
    {
        em.persist(match);
    }

}
