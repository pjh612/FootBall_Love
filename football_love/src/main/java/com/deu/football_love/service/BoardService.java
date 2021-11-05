package com.deu.football_love.service;

import com.deu.football_love.domain.Board;

public interface BoardService {
    Board newBoard(Board board,String teamName);
    boolean deleteBoard(Long id);
}
