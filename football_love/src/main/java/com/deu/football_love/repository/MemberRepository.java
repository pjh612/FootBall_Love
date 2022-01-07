package com.deu.football_love.repository;

import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.member.QueryMemberDto;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberRepository {
    Long insertMember(Member member);

    void deleteMember(Member member);

    Member selectMember(Long id);

    List<QueryMemberDto> selectQueryMemberDto(Long number);

    Member selectMemberById(String id);

    int isDuplicationId(String id);

    int isDuplicationEmail(String email);

    //Member updateMember(JoinRequest joinRequest);

    void updateWithdraw(Member member);

    Long chkWithdraw(String id);

    TeamMemberType selectMemberAuthority(String memberId, String teamId);

    void deleteWithdrawalMemberByDate(LocalDateTime cur, Long day);
}
