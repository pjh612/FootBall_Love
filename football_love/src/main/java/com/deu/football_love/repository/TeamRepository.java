package com.deu.football_love.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.deu.football_love.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

  Optional<Team> findByName(@Param("name") String teamName);

  boolean existsTeamByName(@Param("name") String teamName);

  @Query("SELECT t FROM Team t join fetch t.teamMembers WHERE t.name = :name")
  Optional<Team> findByNameWithTeamMember(@Param("name") String teamName);

  @Query("SELECT t FROM Team t join fetch t.teamMembers WHERE t.id = :id")
  Optional<Team> findByIdWithTeamMember(@Param("id") Long teamId);
}
