package com.deu.football_love.service;

import com.deu.football_love.dto.*;
import org.junit.Test;

public interface MemberService {
	boolean isDuplicationId(String id);

	boolean isDuplicationEmail(String email);

	MemberResponse login(LoginRequest loginRequest);

	MemberResponse join(JoinRequest joinRequest);

	MemberResponse findMember(Long number);

	MemberResponse findMemberById(String id);

	MemberResponse modifyByMemberNumber(Long memberNum, UpdateMemberRequest request);

	MemberResponse modifyByMemberId(String memberId, UpdateMemberRequest request);

	boolean withdraw(String id);

	String checkMemberAuthority(String memberId, String teamName);

	LoginMemberResponse findMemberById_jwt(String id);
	//Jwt Test
	LoginInfo login_jwt(LoginRequest loginRequest);
}
