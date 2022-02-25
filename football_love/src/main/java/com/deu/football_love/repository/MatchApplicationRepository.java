package com.deu.football_love.repository;

import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.dto.match.QueryMatchApplicationDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchApplicationRepository extends JpaRepository<MatchApplication, Long> {

  @Query(
      "SELECT new com.deu.football_love.dto.match.QueryMatchApplicationDto(ma.id, t.id, m.id, ma.state, ma.refuseMessage) "
          + "FROM MatchApplication ma "
          + "JOIN ma.match m "
          + "JOIN ma.team t "
          + "WHERE m.id=:matchId")
  List<QueryMatchApplicationDto> findDtoListByMatchId(@Param("matchId") Long matchId);

  @Query(
      "SELECT ma "
          + "FROM MatchApplication ma "
          + "LEFT JOIN FETCH ma.match m "
          + "LEFT JOIN FETCH ma.team t "
          + "WHERE ma.id=:id")
  Optional<MatchApplication> findByIdWithJoin(@Param("id") Long id);
}
