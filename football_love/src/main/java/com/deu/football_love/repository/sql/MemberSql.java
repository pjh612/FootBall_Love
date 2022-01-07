package com.deu.football_love.repository.sql;

public class MemberSql {

	public static final String COUNT_ID = "SELECT COUNT(m)>0 "
			+ "FROM MEMBER m "
			+ "WHERE m.ID = :id";

	public static final String COUNT_EMAIL = "SELECT COUNT(m)>0 "
			+ "FROM MEMBER m "
			+ "WHERE m.email = :email";

}
