package com.deu.football_love.repository;

import com.deu.football_love.domain.Member;

public interface MemberRepository {
	Long insertMember(Member member);

	Member selectMember(Long id);

	Member selectMemberById(String id);

	int isDuplicationId(String id);

	int isDuplicationEmail(String email);

	//Member updateMember(JoinRequest joinRequest);

	void updateWithdraw(String memberId);

	Long chkWithDraw(String id);

	String selectMemberAuthority(String memberId, String teamId);
}
