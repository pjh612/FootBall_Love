package com.deu.football_love.repository;

import com.deu.football_love.domain.ApplicationJoinTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplicationJoinTeamRepository extends JpaRepository<ApplicationJoinTeam, Long> {
    Optional<ApplicationJoinTeam> findByTeamIdAndMemberId(@Param("teamId") Long teamId, @Param("memberId") String memberId);
}
