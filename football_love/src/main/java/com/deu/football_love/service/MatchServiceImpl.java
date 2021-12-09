package com.deu.football_love.service;

import com.deu.football_love.domain.Matches;
import com.deu.football_love.domain.Stadium;
import com.deu.football_love.domain.Team;
import com.deu.football_love.dto.match.CreateMatchResponse;
import com.deu.football_love.dto.match.QueryMatchDto;
import com.deu.football_love.repository.MatchRepositoryImpl;
import com.deu.football_love.repository.StadiumRepositoryImpl;
import com.deu.football_love.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public CreateMatchResponse addMatch(Long teamId, Long stadiumId, LocalDateTime reservationTime) {
        Team findTeam = teamRepository.selectTeam(teamId);
        Stadium findStadium = stadiumRepository.selectStadium(stadiumId);

        Matches newMatch = new Matches();
        newMatch.setTeam(findTeam);
        newMatch.setStadium(findStadium);
        newMatch.setReservationTime(reservationTime);
        newMatch.setApproval(false);
        matchRepository.insertMatch(newMatch);
        return CreateMatchResponse.from(newMatch);
    }
}
