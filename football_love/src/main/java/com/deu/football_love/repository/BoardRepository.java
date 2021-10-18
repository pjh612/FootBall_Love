package com.deu.football_love.repository;

import com.deu.football_love.domain.Board;

public interface BoardRepository {

    Board insertNewBoard(Board board);
    boolean deleteBoard(Long id);
}
