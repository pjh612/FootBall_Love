package com.deu.football_love.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.Team;
import com.deu.football_love.dto.BoardRequest;
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
	public boolean add(BoardRequest boardRequest) {
		if(boardRepository.countBoardByType(boardRequest.getTeamId(), boardRequest.getBoardType())>0) {
			return false;
		}
		Board board = new Board();
		board.setBoardName(boardRequest.getBoardName());
		board.setBoardType(boardRequest.getBoardType());
		
		Long teamId = boardRequest.getTeamId();
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


}
