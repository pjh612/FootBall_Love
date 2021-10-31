package com.deu.football_love.service;

import com.deu.football_love.domain.Board;
import com.deu.football_love.repository.BoardRepository;
import com.deu.football_love.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final TeamRepository teamRepository;

    @Override
    public Board newBoard(Board board, String teamName) {
        board.setTeam(teamRepository.selectTeam(teamName));
       return boardRepository.insertNewBoard(board);
    }

    @Override
    public boolean deleteBoard(Long id) {
        return boardRepository.deleteBoard(id);
    }
}
