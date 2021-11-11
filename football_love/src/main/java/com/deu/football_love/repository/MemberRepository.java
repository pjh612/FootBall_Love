package com.deu.football_love.repository;

import com.deu.football_love.domain.Member;
import com.deu.football_love.dto.MemberDto;

public interface MemberRepository {
	void insertMember(Member member);

	Member selectMember(String id);

	int isDuplicationId(String id);

	int isDuplicationEmail(String email);

	Member updateMember(MemberDto memberDto);

	void updateWithdraw(String id);

	int chkWithDraw(String id);

	String selectMemberAuthority(String memberId, String teamId);
}
