package com.deu.football_love.service;

import com.deu.football_love.domain.Member;

public interface MemberService {
	boolean isDuplicationId(String id);

	Member login(String id, String password);

	Member join(Member member);
}
