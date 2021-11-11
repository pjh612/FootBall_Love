package com.deu.football_love.repository.sql;

public class MemberSql {

	public static final String COUNT_ID = "SELECT COUNT(*)>0 "
			+ "FROM MEMBER "
			+ "WHERE member_id = ?";

	public static final String COUNT_EMAIL = "SELECT COUNT(*)>0 "
			+ "FROM MEMBER "
			+ "WHERE member_email = ?";

	public static final String COUNT_WITHDRAW = "SELECT COUNT(*)>0 "
			+ "FROM MEMBER "
			+ "INNER JOIN WITHDRAWAL_MEMBER "
			+ "ON WITHDRAWAL_MEMBER.ID = MEMBER.ID "
			+ "WHERE MEMBER.ID = ?";
	
	public static final String GET_MEMBER_AUTH = "SELECT AUTHORITY "
			+ "FROM TEAM_MEMBER "
			+ "INNER JOIN MEMBER "
			+ "ON TEAM_MEMBER.MEMBER_ID = MEMBER.ID "
			+ "INNER JOIN TEAM "
			+ "ON TEAM_MEMBER.TEAM_ID = TEAM.ID "
			+ "WHERE MEMBER.ID = ? AND TEAM.ID = ?";
}
