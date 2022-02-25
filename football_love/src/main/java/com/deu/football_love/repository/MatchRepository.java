package com.deu.football_love.repository;

import com.deu.football_love.domain.type.MatchState;
import com.deu.football_love.dto.match.QueryMatchDto;
import com.deu.football_love.repository.StadiumRepository.StadiumId;
import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;
import java.time.LocalDateTime;

import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.domain.Matches;
import com.deu.football_love.domain.Stadium;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends JpaRepository<Matches, Long> {

  @Query(
      "SELECT new com.deu.football_love.dto.match.QueryMatchDto(m.id, ta.id,ta.name,tb.id, tb.name, m.stadium.id,m.reservationTime, m.state, m.refuseMessage) "
          + "FROM Matches m "
          + "JOIN m.stadium s "
          + "JOIN m.teamA ta "
          + "JOIN m.teamB tb "
          + "WHERE s.id IN(:stadiums)")
  List<QueryMatchDto> findAllByStadiumIdList(@Param("stadiums") List<Long> stadiums);


  @Query("SELECT m "
      + "FROM Matches m "
      + "JOIN m.stadium "
      + "LEFT JOIN m.teamA "
      + "LEFT JOIN m.teamB "
      + "WHERE m.state = :state")
  Page<Matches> findAllMatchByState(@Param("state") MatchState state, Pageable pageable);

}
