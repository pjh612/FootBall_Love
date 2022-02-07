package com.deu.football_love.repository;

import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.WithdrawalMember;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WithdrawalMemberRepository extends JpaRepository<WithdrawalMember, Long> {
    @Query("SELECT wm FROM WithdrawalMember wm JOIN Member m ON m.id=wm.member.id AND m.id=:memberId")
    List<WithdrawalMember> findWithPagingByMemberId(@Param("memberId") String memberId, Pageable pageable);

    default boolean existsByMemberId(String memberId) {
        return findWithPagingByMemberId( memberId, PageRequest.of(0, 1)).size() == 1 ? true : false;
    }
}
