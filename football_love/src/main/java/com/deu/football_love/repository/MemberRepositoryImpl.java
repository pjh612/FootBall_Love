package com.deu.football_love.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.deu.football_love.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
	
	private static final String COUNT_ID = "SELECT COUNT(*)>0 FROM MEMBER WHERE member_id = ?"; 
	
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
		Query query = em.createNativeQuery(COUNT_ID).setParameter(1, id);
		List<BigInteger> list = query.getResultList();
		return list.get(0).intValue();
	}



}
