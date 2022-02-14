package com.deu.football_love.service;

import com.deu.football_love.repository.StadiumRepository.StadiumId;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.deu.football_love.repository.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.domain.Matches;
import com.deu.football_love.domain.Stadium;
import com.deu.football_love.domain.Team;
import com.deu.football_love.dto.match.AddMatchResponse;
import com.deu.football_love.dto.match.MatchApplicationResponse;
import com.deu.football_love.dto.match.MatchApproveResponse;
import com.deu.football_love.dto.match.ModifyMatchResponse;
import com.deu.football_love.dto.match.QueryMatchDto;

import lombok.RequiredArgsConstructor;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchService {

  private final MatchApplicationRepository matchApplicationRepository;
  private final MatchRepository matchRepository;
  private final TeamRepository teamRepository;
  private final StadiumRepository stadiumRepository;

  @Transactional(readOnly = true)
  public QueryMatchDto findMatch(Long matchId) {
    Matches findMatch = matchRepository.findById(matchId)
        .orElseThrow(() -> new IllegalArgumentException("no such stadium data"));
    if (findMatch == null) {
      return null;
    }
    return QueryMatchDto.from(findMatch);
  }

  @Transactional(readOnly = true)
  public List<QueryMatchDto> findAllByCompanyId(Long companyId) {
    List<Long> stadiumIdList = stadiumRepository.findAllIdsByCompanyId(companyId).stream().map(id->id.getId()).collect(
        Collectors.toList());
    List<QueryMatchDto> matchList = matchRepository.findAllByStadiumIdList(stadiumIdList);
    return matchList;
  }

  @Transactional
  public AddMatchResponse addMatch(Long teamId, Long stadiumId, LocalDateTime reservationTime) {
    Team findTeam = teamRepository.findById(teamId)
        .orElseThrow(() -> new IllegalArgumentException("no such Team data"));
    ;
    Stadium findStadium = stadiumRepository.findById(stadiumId)
        .orElseThrow(() -> new IllegalArgumentException("no such stadium data"));

    Matches newMatch = new Matches();
    newMatch.setTeam(findTeam);
    newMatch.setStadium(findStadium);
    newMatch.setReservationTime(reservationTime);
    newMatch.setApproval(false);
    matchRepository.save(newMatch);
    return AddMatchResponse.from(newMatch);
  }

  public MatchApproveResponse approveMatch(Long matchId, Long matchApplicationId) {
    Matches findMatch = matchRepository.findById(matchId)
        .orElseThrow(() -> new IllegalArgumentException("no such match data."));
    findMatch.setApproval(true);
    MatchApplication findApplication = matchApplicationRepository.findById(matchApplicationId)
        .orElseThrow(() -> new IllegalArgumentException("no such match application data."));
    findApplication.setApproval(true);
    return MatchApproveResponse.from(findMatch, findApplication);
  }

  public MatchApplicationResponse addMatchApplication(Long teamId, Long matchId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new IllegalArgumentException("no such Team data"));
    Matches matches = matchRepository.findById(matchId)
        .orElseThrow(() -> new IllegalArgumentException("no such match data."));

    MatchApplication matchApplication = new MatchApplication();
    matchApplication.setTeam(team);
    matchApplication.setMatches(matches);
    matchApplication.setApproval(false);
    matchApplicationRepository.save(matchApplication);

    return MatchApplicationResponse.from(matchApplication);
  }

  public void cancelMatch(Long matchId) {
    Matches match = matchRepository.findById(matchId)
        .orElseThrow(() -> new IllegalArgumentException("no such match data."));
    matchRepository.delete(match);
  }

  public ModifyMatchResponse modifyMatch(Long matchId, Long stadiumId,
      LocalDateTime reservationTime) {
    Stadium stadium = stadiumRepository.findById(stadiumId)
        .orElseThrow(() -> new IllegalArgumentException("no such stadium data"));
    Matches findMatch = matchRepository.findById(matchId)
        .orElseThrow(() -> new IllegalArgumentException("no such match data."));
    findMatch.setStadium(stadium);
    findMatch.setReservationTime(reservationTime);
    return ModifyMatchResponse.from(findMatch);
  }
}
