package com.deu.football_love.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.repository.sql.BoardSql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardRepositoryImpl implements BoardRepository {

	private final EntityManager em;

	@Override
	public Board insertBoard(Board board) {
		em.persist(board);
		return board;
	}

	@Override
	public void deleteBoard(Board board) {
		em.remove(board);
	}

	@Override
	public int countBoardByType(Long teamId, BoardType boardType) {
		Query query = em.createNativeQuery(BoardSql.FIND_BOARD_BY_TYPE).setParameter(1, teamId).setParameter(2,
				boardType);
		List<BigInteger> list = query.getResultList();
		return list.get(0).intValue();
	}

	public Board selectBoardByTeamIdAndBoardName(String boardName, Long teamId)
	{
		List<Board> board = em.createQuery("SELECT b FROM Board b " +
				"join fetch b.team t " +
				"WHERE t.id = :teamId AND b.boardName = :boardName", Board.class)
				.setParameter("teamId", teamId)
				.setParameter("boardName", boardName)
				.getResultList();
		if (board.size() == 1)
			return board.get(0);
		return null;
	}

	@Override
	public Board selectBoardById(Long boardId) {
		return em.find(Board.class, boardId);
	}

}
