package com.deu.football_love.repository;

import com.deu.football_love.domain.Member;

public interface MemberRepository {
	Member insertMember(Member member);

	Member selectMember(String id);
	
	int isDuplicationId(String id);
}
