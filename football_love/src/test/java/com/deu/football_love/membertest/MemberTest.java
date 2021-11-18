package com.deu.football_love.membertest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deu.football_love.domain.Member;
import com.deu.football_love.dto.MemberDto;
import com.deu.football_love.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@SpringBootTest
@Transactional
public class MemberTest {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MemberService memberService;
	@Autowired
	private EntityManager em;
	@Test
	public void member_비밀번호_암호화_테스트() {
		String password = "123456789";
		String encodePassword = passwordEncoder.encode(password);
		assertAll(
				() -> assertNotEquals(password, encodePassword),
				() -> assertTrue(passwordEncoder.matches(password, encodePassword))
		);

	}

	@Test
	public void 멤버_가입()
	{
		Member member = new Member();
		member.setId("pjh612");
		member.setName("jinhpark");
		String encodePassword = passwordEncoder.encode("1234");
		member.setPwd(encodePassword);
		MemberDto memberDto = new MemberDto(member);
		memberService.join(memberDto);
		assertEquals(member, memberService.findMember("pjh612"));
	}
}
