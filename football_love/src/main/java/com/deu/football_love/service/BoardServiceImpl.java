package com.deu.football_love.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.Team;
import com.deu.football_love.dto.BoardDto;
import com.deu.football_love.repository.BoardRepository;
import com.deu.football_love.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	private final BoardRepository boardRepository;
	private final TeamRepository teamRepository;

	@Override
	public boolean add(BoardDto boardDto) {
		if(boardRepository.countBoardByType(boardDto.getTeamId(), boardDto.getBoardType())>0) {
			return false;
		}
		Board board = new Board();
		board.setBoardName(boardDto.getBoardName());
		board.setBoardType(boardDto.getBoardType());
		
		Long teamId = boardDto.getTeamId();
		Team team = teamRepository.selectTeam(teamId);
		board.setTeam(team);
		if (board.getTeam() == null) {
			return false;
		}

		boardRepository.insertBoard(board);
		return true;
	}

	@Override
	public void delete(Long boardId) {
		boardRepository.deleteBoard(boardId);
	}

	@Override
	public BoardDto findById(Long boardId) {
		Board board = boardRepository.selectBoardById(boardId);
		return new BoardDto(board);
	}

	
}
