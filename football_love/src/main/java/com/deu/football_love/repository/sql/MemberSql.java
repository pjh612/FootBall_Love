package com.deu.football_love.repository.sql;

public class MemberSql {

	public static final String COUNT_ID = "SELECT COUNT(m)>0 "
			+ "FROM MEMBER m "
			+ "WHERE m.ID = :id";

	public static final String COUNT_EMAIL = "SELECT COUNT(m)>0 "
			+ "FROM MEMBER m "
			+ "WHERE m.email = :email";
	
	public static final String GET_MEMBER_AUTH = "SELECT tm.authority "
			+ "FROM TEAM_MEMBER tm "
			+ "INNER JOIN MEMBER m"
			+ "ON tm.m.id = m.id "
			+ "INNER JOIN TEAM t"
			+ "ON tm.t.id = t.id "
			+ "WHERE m.id = :m_id AND t.id = :t_id";
}
