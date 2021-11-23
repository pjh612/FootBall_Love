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
	public void deleteBoard(Long boardId) {
		Board board = em.find(Board.class, boardId);
		em.remove(board);
	}

	@Override
	public int countBoardByType(Long teamId, BoardType boardType) {
		Query query = em.createNativeQuery(BoardSql.FIND_BOARD_BY_TYPE).setParameter(1, teamId).setParameter(2,
				boardType);
		List<BigInteger> list = query.getResultList();
		return list.get(0).intValue();
	}

	@Override
	public Board selectBoardById(Long boardId) {
		return em.find(Board.class, boardId);
	}

}
