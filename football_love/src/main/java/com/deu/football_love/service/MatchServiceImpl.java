package com.deu.football_love.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.domain.Matches;
import com.deu.football_love.domain.Stadium;
import com.deu.football_love.domain.Team;
import com.deu.football_love.dto.match.MatchResponse;
import com.deu.football_love.dto.match.ModifyMatchResponse;
import com.deu.football_love.dto.match.QueryMatchDto;
import com.deu.football_love.repository.MatchRepositoryImpl;
import com.deu.football_love.repository.StadiumRepositoryImpl;
import com.deu.football_love.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

	private final MatchRepositoryImpl matchRepository;
	private final TeamRepository teamRepository;
	private final StadiumRepositoryImpl stadiumRepository;

	@Override
	@Transactional(readOnly = true)
	public QueryMatchDto findMatch(Long matchId) {
		Matches findMatch = matchRepository.selectMatch(matchId);
		if (findMatch == null)
			return null;
		return QueryMatchDto.from(findMatch);
	}

	@Override
	@Transactional
	public MatchResponse addMatch(Long teamId, Long stadiumId, LocalDateTime reservationTime) {
		Team findTeam = teamRepository.selectTeam(teamId);
		Stadium findStadium = stadiumRepository.selectStadium(stadiumId);

		Matches newMatch = new Matches();
		newMatch.setTeam(findTeam);
		newMatch.setStadium(findStadium);
		newMatch.setReservationTime(reservationTime);
		newMatch.setApproval(false);
		matchRepository.insertMatch(newMatch);
		return MatchResponse.from(newMatch);
	}

	@Override
	public MatchResponse approveMatch(Long matchId, Long matchApplicationId) {
		Matches matches = matchRepository.updateMatch(matchId);
		MatchApplication matchApplication = matchRepository.updateMatchApplication(matchApplicationId);
		return MatchResponse.from(matches);
	}

	@Override
	public MatchResponse addMatchApplication(Long teamId, Long matchId) {
		Team team = teamRepository.selectTeam(teamId);
		Matches matches = matchRepository.selectMatch(matchId);

		MatchApplication matchApplication = new MatchApplication();
		matchApplication.setTeam(team);
		matchApplication.setMatches(matches);
		matchApplication.setApproval(false);
		matchRepository.insertMatchApplication(matchApplication);

		return MatchResponse.from(matchApplication);
	}

	@Override
	public void cancelMatch(Long matchId) {
		Matches match = matchRepository.selectMatch(matchId);
		matchRepository.deleteMatch(match);
	}

	@Override
	public ModifyMatchResponse modifyMatch(Long matchId, Long stadiumId, LocalDateTime reservationTime) {
		Stadium stadium = stadiumRepository.selectStadium(stadiumId);
		Matches match = matchRepository.updateMatch(matchId, stadium, reservationTime);
		return ModifyMatchResponse.from(match);
	}
}
