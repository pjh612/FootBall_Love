package com.deu.football_love.membertest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.deu.football_love.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberTest {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void member_비밀번호_암호화_테스트() {
		String password = "123456789";
		String encodePassword = passwordEncoder.encode(password);
		assertAll(
				() -> assertNotEquals(password, encodePassword),
				() -> assertTrue(passwordEncoder.matches(password, encodePassword))
		);

	}

}
