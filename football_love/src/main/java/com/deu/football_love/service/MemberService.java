package com.deu.football_love.service;

import com.deu.football_love.dto.MemberDto;

public interface MemberService {
	boolean isDuplicationId(String id);

	boolean isDuplicationEmail(String email);

	MemberDto login(String id, String password);

	MemberDto join(MemberDto memberDto);

	MemberDto findMember(String id);

	MemberDto modify(MemberDto memberDto);

	boolean withdraw(String id);

	String checkMemberAuthority(String memberId, String teamId);
}
