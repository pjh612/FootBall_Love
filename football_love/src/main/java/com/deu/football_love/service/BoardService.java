package com.deu.football_love.service;

import com.deu.football_love.domain.Board;

public interface BoardService {
    Board newBoard(Board board,Long id);
    boolean deleteBoard(Long id);
}
