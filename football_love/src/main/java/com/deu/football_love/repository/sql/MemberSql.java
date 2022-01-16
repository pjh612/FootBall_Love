package com.deu.football_love.repository.sql;

public class MemberSql {

	public static final String COUNT_ID = "SELECT count(m) "
			+ "FROM Member m "
			+ "WHERE m.id = :id";

	public static final String COUNT_EMAIL = "SELECT count(m) "
			+ "FROM Member m "
			+ "WHERE m.email = :email";

}
