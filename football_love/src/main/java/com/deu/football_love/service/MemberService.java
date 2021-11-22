package com.deu.football_love.service;

import com.deu.football_love.dto.JoinRequest;
import com.deu.football_love.dto.LoginRequest;
import com.deu.football_love.dto.MemberResponse;

public interface MemberService {
	boolean isDuplicationId(String id);

	boolean isDuplicationEmail(String email);

	MemberResponse login(LoginRequest loginRequest);

	MemberResponse join(JoinRequest joinRequest);

	MemberResponse findMember(Long number);

	MemberResponse findMemberById(String id);

	MemberResponse modify(JoinRequest joinRequest);

	boolean withdraw(String id);

	String checkMemberAuthority(String memberId, String teamName);
}
