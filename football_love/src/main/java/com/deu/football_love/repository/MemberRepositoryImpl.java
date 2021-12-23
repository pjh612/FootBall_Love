package com.deu.football_love.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.deu.football_love.dto.member.QueryMemberDto;
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
	public void deleteMember(Member member)
	{
		em.remove(member);
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
	public List<QueryMemberDto> selectQueryMemberDto(Long number) {
		return em.createQuery("select new com.deu.football_love.dto.member.QueryMemberDto(m)" +
				" from Member m" +
				" join m.teamMembers" +
				" join m.company" +
				" where m.number = :memberNumber",QueryMemberDto.class)
				.setParameter("memberNumber", number)
				.getResultList();
	}

	@Override
	public int isDuplicationId(String id) {
		List<Long> list = em.createQuery(MemberSql.COUNT_ID,Long.class)
				.setParameter("id", id)
				.getResultList();
		
		return list.get(0).intValue();
	}

	@Override
	public void updateWithdraw(Member member) {
		WithdrawalMember state = new WithdrawalMember(member);
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
