package com.deu.football_love.repository;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;

import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.company.QueryCompanyDto;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.team.QueryTeamMemberDto;
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
    public void deleteMember(Member member) {
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
        if (result.size() == 0)
            return null;
        return result.get(0);
    }

    public List<QueryTeamMemberDto> selectQueryTeamMemberDto(Long memberNumber) {
        return em.createQuery(
                "select new com.deu.football_love.dto.team.QueryTeamMemberDto(tm)" +
                        " from TeamMember tm" +
                        " join tm.member" +
                        " where tm.member.number = :memberNumber", QueryTeamMemberDto.class)
                .setParameter("memberNumber", memberNumber)
                .getResultList();
    }

    public List<QueryCompanyDto> selectQueryCompanyDto(Long memberNumber) {
        return em.createQuery(
                "select new com.deu.football_love.dto.company.QueryCompanyDto(c)" +
                        " from Company c" +
                        " join c.owner" +
                        " where c.owner.number = :memberNumber", QueryCompanyDto.class)
                .setParameter("memberNumber", memberNumber)
                .getResultList();
    }

    public List<QueryMemberDto> selectQueryMemberDtoByNumber(Long number) {
        List<QueryMemberDto> resultList = em.createQuery("select new com.deu.football_love.dto.member.QueryMemberDto(m)" +
                        " from Member m" +
                        " where m.number = :memberNumber"
                , QueryMemberDto.class)
                .setParameter("memberNumber", number)
                .getResultList();
        return  resultList;
    }

    @Override
    public List<QueryMemberDto> selectQueryMemberDto(Long number) {
        List<QueryMemberDto> queryMemberDtos = selectQueryMemberDtoByNumber(number);

        if (queryMemberDtos.size() != 0) {
            queryMemberDtos.get(0).setTeams(selectQueryTeamMemberDto(number));
            List<QueryCompanyDto> companyResult = selectQueryCompanyDto(number);
            if (companyResult.size() != 0)
                queryMemberDtos.get(0).setCompany(companyResult.get(0));
        }
        return queryMemberDtos;
    }




    @Override
    public int isDuplicationId(String id) {
        List<Long> list = em.createQuery(MemberSql.COUNT_ID, Long.class)
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
    public void deleteWithdrawalMemberByDate(LocalDateTime cur, Long day) {
        LocalDateTime threshold = cur.minusDays(day);
        em.createQuery("DELETE FROM WithdrawalMember wm WHERE wm.createdDate <= :threshold")
                .setParameter("threshold", threshold)
                .executeUpdate();
        em.clear();
    }

    @Override
    public Long chkWithdraw(String id) {
        Long result = em.createQuery("SELECT count(w) FROM WithdrawalMember w where w.member.id = :id", Long.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getSingleResult();

        return result == 1 ? result : null;
    }

    @Override
    public TeamMemberType selectMemberAuthority(String memberId, String teamName) {
        List<TeamMember> result = em.createQuery("SELECT tm "
                + "FROM TeamMember tm "
                + "JOIN FETCH tm.member m "
                + "JOIN FETCH tm.team t "
                + "WHERE m.id = :m_id AND t.name = :t_name", TeamMember.class)
                .setParameter("m_id", memberId)
                .setParameter("t_name", teamName)
                .getResultList();
        return result.size() != 0 ? result.get(0).getType() : TeamMemberType.NONE;
    }

    @Override
    public int isDuplicationEmail(String email) {
        List<Long> list = em.createQuery(MemberSql.COUNT_EMAIL, Long.class)
                .setParameter("email", email)
                .getResultList();
        return list.get(0).intValue();
    }
}
