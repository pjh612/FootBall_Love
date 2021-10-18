package com.deu.football_love.repository;

import com.deu.football_love.domain.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardRepositoryImpl implements BoardRepository{

    private final EntityManager em;
    @Override
    public Board insertNewBoard(Board board) {
        em.persist(board);
        return board;
    }

    @Override
    public boolean deleteBoard(Long id) {
        try {
            em.remove(id);
            return true;
        }
        catch(IllegalArgumentException e)
        {
            log.info("There is no matching data with id");
            return false;
        }
    }
}
