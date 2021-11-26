package com.deu.football_love.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.deu.football_love.config.JwtTokenProvider;
import com.deu.football_love.controller.consts.SessionConst;
import com.deu.football_love.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deu.football_love.domain.Member;
import com.deu.football_love.service.MemberService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisTemplate redisTemplate;

	@ApiOperation(value = "회원가입 요청")
	@PostMapping
	public ResponseEntity<MemberResponse> join(@RequestBody JoinRequest joinRequest) {
		System.out.println("joinRequest.getId() = " + joinRequest.getId());
		//log.info("joinRequest = {}",joinRequest);
		MemberResponse joinMember = memberService.join(joinRequest);
		if (joinMember == null) {
			return new ResponseEntity<MemberResponse>(HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<MemberResponse>(joinMember, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "로그인 요청")
	@PostMapping("/login/{id}")
	public ResponseEntity<MemberResponse> login(@RequestBody LoginRequest loginRequest,
			HttpServletRequest request) {
		MemberResponse member = memberService.login(loginRequest);
		HttpSession session = request.getSession();
		if (member == null) {
			session.invalidate();
			return new ResponseEntity<MemberResponse>(HttpStatus.CONFLICT);
		} else {
			session.setAttribute(SessionConst.SESSION_MEMBER, member);
			return new ResponseEntity<MemberResponse>(member, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "로그인 요청")
	@PostMapping("/login_jwt/{id}")
	public ResponseEntity<LoginInfo> login_jwt(@RequestBody LoginRequest loginRequest,
												HttpServletRequest request) {
		LoginInfo response = memberService.login_jwt(loginRequest);
		log.info("login response = {}", response);
		if (response.getResult().equals("fail")) {
			return new ResponseEntity<LoginInfo>(HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<LoginInfo>(response, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "로그아웃 요청")
	@PostMapping("/logout_jwt")
	public ResponseEntity<LoginInfo> logout_jwt(@RequestBody LoginRequest loginRequest,
											   HttpServletRequest request) {
		return null;
	}

	@ApiOperation(value = "아이디 중복확인 요청")
	@GetMapping("/duplication/id")
	public ResponseEntity isDuplicaitonId(@RequestParam String id) {
		if (memberService.isDuplicationId(id)) {
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
	}

	@ApiOperation(value = "이메일 중복확인 요청")
	@GetMapping("/duplication/email")
	public ResponseEntity isDuplicaitonEmail(@RequestParam String email) {
		if (memberService.isDuplicationEmail(email)) {
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
	}

	@ApiOperation(value = "회원정보 수정요청")
	@PutMapping("/{memberId}")
	public ResponseEntity<MemberResponse> modify(@PathVariable String memberId, @RequestBody UpdateMemberRequest request, HttpSession session) {
		MemberResponse modifiedMember = memberService.modifyByMemberId(memberId, request);
		return new ResponseEntity<MemberResponse>(modifiedMember, HttpStatus.OK);
	}

	@ApiOperation(value = "회원탈퇴 요청", notes = "id와 회원을 확인해 회원탈퇴 요청을 한다.")
	@PutMapping("/withdrawals/{id}")
	public ResponseEntity withdrawMember(@PathVariable String id, HttpSession session) {
		MemberResponse sessionMember = (MemberResponse) session.getAttribute(SessionConst.SESSION_MEMBER);
		if (sessionMember == null) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}

		boolean deleteFlag = memberService.withdraw(id);
		if (deleteFlag) {
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
	}

	@ApiOperation(value = "회원 권한확인 요청")
	@GetMapping("/{memberId}/authority")
	public ResponseEntity<String> checkMemberAuthority(@RequestParam("memberId") String memberId,
			@RequestParam("teamName") String teamName, HttpSession session) {
		Member sessionMember = (Member) session.getAttribute(SessionConst.SESSION_MEMBER);
		if (sessionMember == null) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}

		String authority = memberService.checkMemberAuthority(memberId,teamName);
		if (authority == null) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<String>(authority, HttpStatus.OK);
		}
	}

	/*@ExceptionHandler(Exception.class)
	public ResponseEntity serverException() {
		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
}
