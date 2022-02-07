package com.deu.football_love.service;

import java.time.LocalDateTime;

import com.deu.football_love.repository.StadiumRepository;
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
import com.deu.football_love.repository.MatchRepositoryImpl;
import com.deu.football_love.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService {

	private final MatchRepositoryImpl matchRepository;
	private final TeamRepository teamRepository;
	private final StadiumRepository stadiumRepository;

	@Transactional(readOnly = true)
	public QueryMatchDto findMatch(Long matchId) {
		Matches findMatch = matchRepository.selectMatch(matchId);
		if (findMatch == null)
			return null;
		return QueryMatchDto.from(findMatch);
	}

	@Transactional
	public AddMatchResponse addMatch(Long teamId, Long stadiumId, LocalDateTime reservationTime) {
		Team findTeam = teamRepository.findById(teamId).orElseThrow(()->new IllegalArgumentException("no such Team data"));;
		Stadium findStadium = stadiumRepository.findById(stadiumId).orElseThrow(()-> new IllegalArgumentException("no such stadium data"));

		Matches newMatch = new Matches();
		newMatch.setTeam(findTeam);
		newMatch.setStadium(findStadium);
		newMatch.setReservationTime(reservationTime);
		newMatch.setApproval(false);
		matchRepository.insertMatch(newMatch);
		return AddMatchResponse.from(newMatch);
	}

	public MatchApproveResponse approveMatch(Long matchId, Long matchApplicationId) {
		Matches matches = matchRepository.updateMatch(matchId);
		MatchApplication matchApplication = matchRepository.updateMatchApplication(matchApplicationId);
		return MatchApproveResponse.from(matches,matchApplication);
	}

	public MatchApplicationResponse addMatchApplication(Long teamId, Long matchId) {
		Team team = teamRepository.findById(teamId).orElseThrow(()->new IllegalArgumentException("no such Team data"));
		Matches matches = matchRepository.selectMatch(matchId);

		MatchApplication matchApplication = new MatchApplication();
		matchApplication.setTeam(team);
		matchApplication.setMatches(matches);
		matchApplication.setApproval(false);
		matchRepository.insertMatchApplication(matchApplication);

		return MatchApplicationResponse.from(matchApplication);
	}

	public void cancelMatch(Long matchId) {
		Matches match = matchRepository.selectMatch(matchId);
		matchRepository.deleteMatch(match);
	}

	public ModifyMatchResponse modifyMatch(Long matchId, Long stadiumId, LocalDateTime reservationTime) {
		Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow(()-> new IllegalArgumentException("no such stadium data"));
		Matches match = matchRepository.updateMatch(matchId, stadium, reservationTime);
		return ModifyMatchResponse.from(match);
	}
}
