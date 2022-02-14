package com.deu.football_love.repository;

import com.deu.football_love.dto.match.QueryMatchDto;
import com.deu.football_love.repository.StadiumRepository.StadiumId;
import java.time.LocalDateTime;

import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.domain.Matches;
import com.deu.football_love.domain.Stadium;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends JpaRepository<Matches, Long> {

  @Query(
      "SELECT new com.deu.football_love.dto.match.QueryMatchDto(m.id, t.name, m.stadium.id, m.approval, m.reservationTime) "
          + "FROM Matches m "
          + "JOIN m.stadium s "
          + "JOIN m.team t "
          + "WHERE s.id IN(:stadiums)")
  List<QueryMatchDto> findAllByStadiumIdList(@Param("stadiums") List<Long> stadiums);
}
