package com.deu.football_love.service;

import com.deu.football_love.domain.type.MatchApplicationState;
import com.deu.football_love.domain.type.MatchState;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.match.QueryMatchApplicationDto;
import com.deu.football_love.exception.CustomException;
import com.deu.football_love.exception.NotExistDataException;
import com.deu.football_love.exception.NotTeamMemberException;
import com.deu.football_love.exception.error_code.ErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import com.deu.football_love.repository.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@Transactional
public class MatchService {

  private final MatchApplicationRepository matchApplicationRepository;
  private final MatchRepository matchRepository;
  private final TeamRepository teamRepository;
  private final StadiumRepository stadiumRepository;
  private final TeamService teamService;

  @Transactional(readOnly = true)
  public QueryMatchDto findMatch(Long matchId) {
    Matches findMatch = matchRepository.findById(matchId)
        .orElseThrow(() -> new NotExistDataException("no such stadium data"));
    if (findMatch.getState().equals(MatchState.EMPTY)) {
      return new QueryMatchDto(findMatch.getId(), findMatch.getStadium().getId(), findMatch.getReservationTime(),
          findMatch.getState(), findMatch.getRefuseMessage());
    } else if (findMatch.getState().equals(MatchState.MATCHED) || findMatch.getState().equals(MatchState.OVER)) {
      return
          new QueryMatchDto(findMatch.getId(), findMatch.getTeamA().getId(), findMatch.getTeamA().getName(),
              findMatch.getTeamB().getId(), findMatch.getTeamB().getName(), findMatch.getStadium().getId(),
              findMatch.getReservationTime(), findMatch.getState(), findMatch.getRefuseMessage());
    } else if (findMatch.getState().equals(MatchState.WAITING)) {
      return new QueryMatchDto(findMatch.getId(), findMatch.getTeamA().getId(), findMatch.getTeamA().getName(),
          findMatch.getStadium().getId(), findMatch.getReservationTime(), findMatch.getState(),
          findMatch.getRefuseMessage());
    }
    throw new IllegalArgumentException("There is something error.");
  }

  @Transactional(readOnly = true)
  public Page<QueryMatchDto> findAllMatchByState(MatchState state, Pageable pageable) {
    Page<Matches> matchPageList = matchRepository.findAllMatchByState(state, pageable);
    if (state.equals(MatchState.EMPTY)) {
      return matchPageList.map(
          m -> new QueryMatchDto(m.getId(), m.getStadium().getId(), m.getReservationTime(),
              m.getState(), m.getRefuseMessage()));
    } else if (state.equals(MatchState.MATCHED) || state.equals(MatchState.OVER)) {
      return matchPageList.map(
          m -> new QueryMatchDto(m.getId(), m.getTeamA().getId(), m.getTeamA().getName(),
              m.getTeamB().getId(), m.getTeamB().getName(), m.getStadium().getId(),
              m.getReservationTime(), m.getState(), m.getRefuseMessage()));
    } else if (state.equals(MatchState.WAITING)) {
      return matchPageList.map(
          m -> new QueryMatchDto(m.getId(), m.getTeamA().getId(), m.getTeamA().getName(),
              m.getStadium().getId(), m.getReservationTime(), m.getState(), m.getRefuseMessage()));
    }
    throw new IllegalArgumentException("There is something error.");
  }

  @Transactional(readOnly = true)
  public List<QueryMatchDto> findAllByCompanyId(Long companyId) {
    List<Long> stadiumIdList = stadiumRepository.findAllIdsByCompanyId(companyId).stream()
        .map(id -> id.getId()).collect(
            Collectors.toList());
    List<QueryMatchDto> matchList = matchRepository.findAllByStadiumIdList(stadiumIdList);
    return matchList;
  }

  /**
   * ?????? Match??? ????????? ????????? ??????
   */
  public List<QueryMatchApplicationDto> findAllMatchApplicationByMatchId(Long matchId) {
    return matchApplicationRepository.findDtoListByMatchId(matchId);

  }

  public QueryMatchApplicationDto findMatchApplicationById(Long applicationId) {
    MatchApplication findApplication = matchApplicationRepository.findByIdWithJoin(applicationId)
        .orElseThrow(() -> new IllegalArgumentException("no such application data."));
    return new QueryMatchApplicationDto(findApplication.getId(), findApplication.getTeam().getId(),
        findApplication.getMatch().getId(), findApplication.getState(), findApplication.getRefuseMessage());

  }

  /**
   * Company?????? ????????? ??????
   */
  public AddMatchResponse addMatch(Long stadiumId, Long companyId, LocalDateTime reservationTime) {
    Stadium findStadium = stadiumRepository.findByCompanyIdAndStadiumId(stadiumId, companyId)
        .orElseThrow(() -> new NotExistDataException("no such stadium data"));
    Matches newMatch = new Matches();
    newMatch.setState(MatchState.EMPTY);
    newMatch.setStadium(findStadium);
    newMatch.setReservationTime(reservationTime);
    matchRepository.save(newMatch);
    return AddMatchResponse.from(newMatch);
  }

  /**
   * ?????? teamA??? ??????, EMPTY -> WAITING (??? B??? ?????????)
   */
  public void registerTeamA(Long matchId, Long teamId) {
    Matches findMatch = matchRepository.findById(matchId)
        .orElseThrow(() -> new NotExistDataException("no such match data."));
    if (!findMatch.getState().equals(MatchState.EMPTY)) {
      throw new IllegalArgumentException("There is already team A.");
    }
    Team findTeam = teamRepository.findById(teamId)
        .orElseThrow(() -> new NotExistDataException("no such team data."));
    findMatch.setTeamA(findTeam);
    findMatch.setState(MatchState.WAITING);
  }

  /**
   * ?????? ??????
   */
  public MatchApproveResponse approveMatchApplication(Long matchId, Long matchApplicationId, Long approverNumber) {
    Matches findMatch = matchRepository.findById(matchId)
        .orElseThrow(() -> new NotExistDataException("no such match data."));
    MatchApplication findApplication = matchApplicationRepository.findByIdWithJoin(matchApplicationId)
        .orElseThrow(() -> new NotExistDataException("no such match application data."));
    if (!findMatch.getState().equals(MatchState.WAITING)) {
      throw new CustomException(ErrorCode.NOT_WAITING_MATCH);
    }
    TeamMemberType teamMemberType = teamService.authorityCheck(findMatch.getTeamA().getId(), approverNumber);
    if(!(teamMemberType.equals(TeamMemberType.ADMIN) || teamMemberType.equals(TeamMemberType.LEADER)))
      throw new NotTeamMemberException("you have no authority.");
    findMatch.setState(MatchState.MATCHED);
    findMatch.setTeamB(findApplication.getTeam());
    matchApplicationRepository.delete(findApplication);
    return new MatchApproveResponse(findMatch.getId(), findMatch.getStadium().getId(),
        findMatch.getReservationTime(), findMatch.getTeamA().getId(),
        findApplication.getTeam().getId());
  }

  /**
   * ????????? ?????? ??????
   */
  public MatchApplicationResponse addMatchApplication(Long matchId, Long teamId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new NotExistDataException("no such Team data"));
    Matches match = matchRepository.findById(matchId)
        .orElseThrow(() -> new NotExistDataException("no such match data."));
    if(teamId == match.getTeamA().getId())
      throw new IllegalArgumentException("team B can't be same with team A");
    MatchApplication matchApplication = new MatchApplication();
    matchApplication.setTeam(team);
    matchApplication.setMatch(match);
    matchApplication.setState(MatchApplicationState.WAITING);
    team.getMatchApplications().add(matchApplication);
    matchApplicationRepository.save(matchApplication);
    log.info("match application Id  = {}", matchApplication.getId());
    return new MatchApplicationResponse(matchApplication.getId(), match.getId(), matchApplication.getState());
  }

  /**
   * ?????? ?????? ??????
   */
  public void cancelMatch(Long matchId, Long companyId) {
    Matches match = matchRepository.findById(matchId)
        .orElseThrow(() -> new NotExistDataException("no such match data."));
    if(match.getStadium().getCompany().getId() != companyId)
      throw new CustomException(ErrorCode.NOT_STADIUM_OWNER);
    matchRepository.delete(match);
  }

  public ModifyMatchResponse modifyMatch(Long matchId, Long stadiumId,
      LocalDateTime reservationTime) {
    Stadium stadium = stadiumRepository.findById(stadiumId)
        .orElseThrow(() -> new NotExistDataException("no such stadium data"));
    Matches findMatch = matchRepository.findById(matchId)
        .orElseThrow(() -> new NotExistDataException("no such match data."));
    findMatch.setStadium(stadium);
    findMatch.setReservationTime(reservationTime);
    return ModifyMatchResponse.from(findMatch);
  }

  /**
   * ??? vs ??? ?????? ????????? ??????
   */
  public void refuseMatchApplication(Long applicationId, String refuseMessage, Long memberNumber) {
    MatchApplication findApplication = matchApplicationRepository.findByIdWithJoin(applicationId)
        .orElseThrow(() -> new NotExistDataException("no such match application data."));
    TeamMemberType teamMemberType = teamService
        .authorityCheck(findApplication.getMatch().getTeamA().getId(), memberNumber);
    if (teamMemberType != TeamMemberType.ADMIN && teamMemberType != TeamMemberType.LEADER) {
      throw new CustomException(ErrorCode.NOT_ADMIN);
    }
    if (findApplication.getState() != MatchApplicationState.WAITING) {
      throw new CustomException(ErrorCode.NOT_WAITING_MATCH);
    }
    findApplication.refuse(refuseMessage);

  }
}
