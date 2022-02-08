package com.deu.football_love.repository;

import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.dto.team.QueryTeamListItemDto;
import com.deu.football_love.dto.team.QueryTeamMemberDto;
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

    @Query("SELECT tm " +
            "FROM TeamMember tm " +
            "WHERE tm.team.id =:teamId")
    List<TeamMember> findTeamMembersByTeamId(@Param("teamId") Long teamId);

    @Query("SELECT tm " +
            "FROM TeamMember tm " +
            "JOIN FETCH tm.member m " +
            "JOIN FETCH tm.team t " +
            "WHERE tm.team.id =:teamId AND tm.member.number =:memberNumber")
    Optional<TeamMember> findByTeamIdAndMemberNumber(@Param("teamId") Long teamId, @Param("memberNumber") Long memberNumber);

    @Query("SELECT tm " +
            "FROM TeamMember tm " +
            "JOIN FETCH tm.member m " +
            "JOIN FETCH tm.team t " +
            "WHERE tm.team.id =:teamId AND tm.member.id =:memberId")
    Optional<TeamMember> findByTeamIdAndMemberId(@Param("teamId") Long teamId, @Param("memberId") String memberId);

    @Query("SELECT tm " +
            "FROM TeamMember tm " +
            "JOIN FETCH tm.member m " +
            "JOIN FETCH tm.team t " +
            "WHERE m.id=:memberId AND t.name=:teamName")
    Optional<TeamMember> findByTeamNameAndMemberId(@Param("teamName") String teamName, @Param("memberId") String memberId);

    @Query("SELECT tm FROM TeamMember tm JOIN Member m ON m.id=tm.member.id WHERE tm.team.id =:teamId AND m.id=:memberId")
    List<TeamMember> findWithPagingByTeamIdAndMemberId(@Param("teamId") Long teamId, @Param("memberId") String memberId, Pageable pageable);

    default boolean existsByTeamIdAndMemberId(Long teamId, String memberId) {
        return findWithPagingByTeamIdAndMemberId(teamId, memberId, PageRequest.of(0, 1)).size() == 1 ? true : false;
    }

    @Query("select new com.deu.football_love.dto.team.QueryTeamMemberDto(tm)" +
            " from TeamMember tm" +
            " join tm.member" +
            " join tm.team" +
            " where tm.member.number = :memberNumber")
    List<QueryTeamMemberDto> findQueryTeamMemberDtoByMemberNumber(@Param("memberNumber") Long memberNumber);


    @Query("SELECT new com.deu.football_love.dto.team.QueryTeamListItemDto(t.id, t.name, tm.type, t.teamMembers.size, t.profileImgUri) FROM TeamMember tm JOIN tm.member m JOIN tm.team t WHERE tm.member.number=:memberNumber")
    List<QueryTeamListItemDto> findAllTeamByMemberNumber(@Param("memberNumber") Long memberNumber);

    @Query("SELECT count(tm) FROM TeamMember tm JOIN tm.team WHERE tm.team.id=:teamId")
    Long countTeamMembersByTeamId(@Param("teamId") Long teamId);
}
