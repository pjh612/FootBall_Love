package com.deu.football_love.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deu.football_love.domain.Member;
import com.deu.football_love.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/{id}")
	public ResponseEntity<Member> join(@PathVariable String id, @RequestBody Member member) {
		Member joinMember= memberService.join(member);
		if(joinMember == null) {
			return new ResponseEntity<Member>(HttpStatus.CONFLICT);
		}else {
			return new ResponseEntity<Member>(joinMember,HttpStatus.OK);
		}
	}

	@PostMapping("/login/{id}")
	public ResponseEntity<Member> login(@PathVariable String id, @RequestBody String password,HttpServletRequest request) {
		Member member = memberService.login(id, password);
		HttpSession session = request.getSession();
		if(member == null) {
			session.invalidate();
			return new ResponseEntity<Member>(HttpStatus.CONFLICT);
		}else {
			session.setAttribute("memberInfo", member);
			return new ResponseEntity<Member>(member,HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}/duplication")
	public boolean isDuplicaiton(@PathVariable String id) {
		return memberService.isDuplicationId(id);
	}
}
