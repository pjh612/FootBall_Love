package com.deu.football_love.repository;

import com.deu.football_love.domain.TeamMember;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    @Query("DELETE FROM TeamMember tm WHERE tm.team.id =:teamId AND tm.member.number=:memberNumber")
    @Modifying
    void deleteByTeamIdAndMemberNumber(@Param("teamId") Long teamId, @Param("memberNumber") Long memberNumber);

    @Query("SELECT tm FROM TeamMember tm WHERE tm.team.id =:teamId")
    List<TeamMember> findTeamMembersByTeamId(@Param("teamId") Long teamId);

    @Query("SELECT tm FROM TeamMember tm WHERE tm.team.id =:teamId AND tm.member.number =:memberNumber")
    Optional<TeamMember> findByTeamIdAndMemberNumber(@Param("teamId") Long teamId, @Param("memberNumber") Long memberNumber);

    @Query("SELECT tm FROM TeamMember tm WHERE tm.team.id =:teamId AND tm.member.id =:memberId")
    Optional<TeamMember> findByTeamIdAndMemberId(@Param("teamId") Long teamId, @Param("memberId") String memberId);

    @Query("SELECT tm FROM TeamMember tm JOIN Member m ON m.id=tm.member.id WHERE tm.team.id =:teamId AND m.id=:memberId")
    List<TeamMember> findWithPagingByTeamIdAndMemberId(@Param("teamId") Long teamId, @Param("memberId") String memberId, Pageable pageable);

    default boolean existsByTeamIdAndMemberId(Long teamId,  String memberId)
    {
        return findWithPagingByTeamIdAndMemberId(teamId, memberId, PageRequest.of(0, 1)).size() == 1 ? true : false;
    }



}