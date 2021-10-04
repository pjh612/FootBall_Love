package com.deu.football_love.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.deu.football_love.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
	
	private static final String COUNT_ID = "SELECT COUNT(*) FROM MEMBER"; 
	
	private final EntityManager em;
	
	@Override
	public Member insertMember(Member member) {
		em.persist(member);
		return member;
	}

	@Override
	public Member selectMember(String id) {
		return em.find(Member.class, id);
	}

	@Override
	public int isDuplicationId(String id) {
		Query query = em.createNativeQuery(COUNT_ID);
		return query.getFirstResult();
	}



}
