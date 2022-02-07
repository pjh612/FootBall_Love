package com.deu.football_love.repository;

import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.member.QueryMemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    @Query("select new com.deu.football_love.dto.member.QueryMemberDto(m)" +
            " from Member m" +
            " join m.company" +
            " where m.number = :memberNumber")
    Optional<QueryMemberDto> findQueryMemberDtoByNumber(@Param("memberNumber") Long number);

    Optional<Member> findById(@Param("id") String id);

    boolean existsById(@Param("id") String id);

    boolean existsByEmail(@Param("email") String email);

    @Query("DELETE FROM WithdrawalMember wm WHERE wm.createdDate <= :threshold")
    @Modifying
    void deleteWithdrawalMemberByDate(@Param("threshold") LocalDateTime threshold);
}
