package com.deu.football_love.service;

import com.deu.football_love.domain.Member;

public interface MemberService {
	boolean isDuplicationId(String id);
	
	boolean isDuplicationEmail(String email);

	Member login(String id, String password);

	Member join(Member member);

	Member findMember(String id);
	
	Member modify(Member member);
	
	boolean withdraw(String id);
	
	String checkMemberAuthority(String id);
}
