package com.deu.football_love.member;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.UpdateMemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.JoinRequest;
import com.deu.football_love.dto.LoginRequest;
import com.deu.football_love.dto.MemberResponse;
import com.deu.football_love.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
public class MemberTest {
	private final PasswordEncoder passwordEncoder;
	
	private final MemberService memberService;
	
	@Test
	public void member_비밀번호_암호화_테스트() {
		String password = "123456789";
		String encodePassword = passwordEncoder.encode(password);
		assertAll(() -> assertNotEquals(password, encodePassword),
				() -> assertTrue(passwordEncoder.matches(password, encodePassword)));

	}

	@Test
	public void 멤버_가입() {
		JoinRequest request = new JoinRequest("dbtlwns","1234","금꽁치","유시준",LocalDate.of(1995,5,2), new Address("1", "2", "3"),"simba0502@naver.com" ,"010-6779-3476",MemberType.NORMAL);
		MemberResponse memberResponse = memberService.join(request);
		assertAll(() -> assertEquals(request.getId(), memberResponse.getId()),
				() -> assertEquals(request.getNickname(), memberResponse.getNickname()),
				() -> assertEquals(request.getName(), memberResponse.getName()),
				() -> assertEquals(request.getEmail(), memberResponse.getEmail()));
	}

	@Test
	public void 멤버_로그인() {
		LoginRequest request = new LoginRequest("dbtlwns", "1234");
		MemberResponse memberResponse = memberService.login(request);
		assertAll(() -> assertEquals(request.getId(), memberResponse.getId()),
				() -> assertEquals("금꽁치", memberResponse.getNickname()),
				() -> assertEquals("유시준", memberResponse.getName()),
				() -> assertEquals("simba0502@naver.com", memberResponse.getEmail()));
	}

	@Test
	public void 멤버_찾기() {
		String id = "dbtlwns";
		MemberResponse memberResponse = memberService.findMemberById(id);
		assertAll(() -> assertEquals("dbtlwns", memberResponse.getId()),
				() -> assertEquals("금꽁치", memberResponse.getNickname()),
				() -> assertEquals("유시준", memberResponse.getName()),
				() -> assertEquals("simba0502@naver.com", memberResponse.getEmail()));
	}

	@Test
	public void 멤버_수정() {
		UpdateMemberRequest request = new UpdateMemberRequest("dbtlwns","1234","금꽁치","유시준",LocalDate.of(1995,5,2), new Address("1", "2", "3"),"simba0502@naver.com" ,"010-6779-3476",MemberType.NORMAL);
		MemberResponse memberResponse = memberService.modifyByMemberId(request.getId(), request);
		assertAll(() -> assertEquals("dbtlwns", memberResponse.getId()),
				() -> assertEquals("금꽁치", memberResponse.getNickname()),
				() -> assertEquals("유시준", memberResponse.getName()),
				() -> assertEquals("dbtlwns@naver.com", memberResponse.getEmail()));
	}

	@DisplayName("아이디 중복확인 true일때 아이디 중복")
	@Test
	public void 멤버_아이디중복확인() {
		String id = "dbtlwns";
		assertTrue(memberService.isDuplicationId(id));
	}

	@DisplayName("이메일 중복확인 true일때 이메일 중복")
	@Test
	public void 멤버_이메일중복확인() {
		String email = "simba0502@naver.com";
		assertTrue(memberService.isDuplicationId(email));
	}

	@Test
	public void 멤버_그룹권한확인() {
		String memberId = "dbtlwns";
		String teamName = "FC Flow";
		assertEquals(memberService.checkMemberAuthority(memberId, teamName), TeamMemberType.ADMIN);
	}

	@DisplayName("회원탈퇴 true일때 성공")
	@Test
	public void 멤버_탈퇴() {
		String id = "dbtlwns";
		assertTrue(memberService.withdraw(id));
	}
}
