package com.deu.football_love.repository.sql;

public class MemberSql {

	public static final String COUNT_ID = "SELECT count(m) "
			+ "FROM Member m "
			+ "WHERE m.id = :id";

	public static final String COUNT_EMAIL = "SELECT count(m) "
			+ "FROM Member m "
			+ "WHERE m.email = :email";
	
	public static final String GET_MEMBER_AUTH = "SELECT tm.authority "
			+ "FROM TEAM_MEMBER tm "
			+ "INNER JOIN MEMBER m"
			+ "ON tm.m.id = m.id "
			+ "INNER JOIN TEAM t"
			+ "ON tm.t.id = t.id "
			+ "WHERE m.id = :m_id AND t.id = :t_id";
}
