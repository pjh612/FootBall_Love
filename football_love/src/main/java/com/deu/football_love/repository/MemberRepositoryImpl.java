package com.deu.football_love.repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.WithdrawalMember;
import com.deu.football_love.repository.sql.MemberSql;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

	private final EntityManager em;

	@Override
	public Long insertMember(Member member) {
		em.persist(member);
		return member.getNumber();
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
		List<Long> list = em.createQuery(MemberSql.COUNT_ID,Long.class)
				.setParameter("id", id)
				.getResultList();
		
		return list.get(0).intValue();
	}

	@Override
	public void updateWithdraw(String memberId) {
		List<Member> findMember = em.createQuery("SELECT m FROM Member m where m.id = :id", Member.class).setParameter("id", memberId).getResultList();
		WithdrawalMember state = new WithdrawalMember();
		state.setMember(findMember.get(0));
		em.persist(state);
	}

	@Override
	public Long chkWithDraw(String id) {
		List<Long> result = em.createQuery("SELECT count(w) FROM WithdrawalMember w where w.member.id = :id",Long.class)
				.setParameter("id", id)
				.getResultList();

		return result.size() == 1 ? result.get(0) : null;
	}

	@Override
	public String selectMemberAuthority(String memberId, String teamId) {
		List<String> list = em.createQuery(MemberSql.GET_MEMBER_AUTH,String.class)
				.setParameter("m_id", memberId)
				.setParameter("t_id", teamId)
				.getResultList();
		return list.get(0);
	}

	@Override
	public int isDuplicationEmail(String email) {
		List<Long> list = em.createQuery(MemberSql.COUNT_EMAIL,Long.class)
				.setParameter("email", email)
				.getResultList();
		return list.get(0).intValue();
	}
}
