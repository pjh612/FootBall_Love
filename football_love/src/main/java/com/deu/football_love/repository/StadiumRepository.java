package com.deu.football_love.repository;

import com.deu.football_love.domain.Stadium;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class StadiumRepository {

    private final EntityManager em;

    public Stadium selectStadium(Long stadiumId)
    {
        return em.find(Stadium.class, stadiumId);
    }

    public void insertStadium(Stadium stadium)
    {
        em.persist(stadium);
    }

    public void deleteStadium(Stadium stadium)
    {
        em.remove(stadium);
    }
}
