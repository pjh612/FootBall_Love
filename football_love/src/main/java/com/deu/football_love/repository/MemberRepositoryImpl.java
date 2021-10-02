package com.deu.football_love.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.deu.football_love.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
	private final EntityManager em;
	
	@Override
	public Member insertMember(Member member) {
		em.persist(member);
		em.flush();
		return member;
	}

	@Override
	public Member selectMember(String id, String password) {
		return em.find(Member.class, id);
	}

	@Override
	public int isDuplicationId(String id) {
		return 0;
	}



}
