package com.deu.football_love.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.WithdrawalMember;
import com.deu.football_love.dto.JoinRequest;
import com.deu.football_love.dto.MemberResponse;
import com.deu.football_love.repository.sql.MemberSql;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

	private final EntityManager em;

	@Override
	public void insertMember(Member member) {
		em.persist(member);
	}

	@Override
	public Member selectMember(Long number) {
		return em.find(Member.class, number);
	}

	@Override
	public Member selectMemberById(String id) {
		List<Member> result = em.createQuery("select m from Member m where m.id = :id", Member.class)
				.setParameter("id", id)
				.getResultList();
		if (result == null)
			return null;
		return result.get(0);
	}

	@Override
	public int isDuplicationId(String id) {
		Query query = em.createNativeQuery(MemberSql.COUNT_ID).setParameter(1, id);
		List<BigInteger> list = query.getResultList();
		return list.get(0).intValue();
	}

	@Override
	public Member updateMember(JoinRequest joinRequest) {
		String id = joinRequest.getId();
		Member member = em.find(Member.class, id);
		return member;
	}

	@Override
	public void updateWithdraw(String id) {
		Member member = em.find(Member.class, id);
		WithdrawalMember state = new WithdrawalMember();
		em.persist(state);
		state.setMember(member);
	}

	@Override
	public int chkWithDraw(String id) {
		Query query = em.createNativeQuery(MemberSql.COUNT_WITHDRAW).setParameter(1, id);
		List<BigInteger> list = query.getResultList();
		return list.get(0).intValue();
	}

	@Override
	public String selectMemberAuthority(String memberId, String teamId) {
		Query query = em.createNativeQuery(MemberSql.GET_MEMBER_AUTH).setParameter(1, memberId).setParameter(2, teamId);
		List<String> list = query.getResultList();
		return list.get(0);
	}

	@Override
	public int isDuplicationEmail(String email) {
		Query query = em.createNativeQuery(MemberSql.COUNT_EMAIL).setParameter(1, email);
		List<BigInteger> list = query.getResultList();
		return list.get(0).intValue();
	}
}
