package com.deu.football_love.repository.sql;

public class BoardSql {
	public static final String FIND_BOARD_BY_TYPE = "SELECT COUNT(*)>0 "
			+ "FROM BOARD "
			+ "WHERE BOARD_ID = ? "
			+ "AND TEAM_NAME = ? "
			+ "AND BOARD_TYPE = ?";
}
