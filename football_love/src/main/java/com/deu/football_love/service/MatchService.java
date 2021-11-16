package com.deu.football_love.service;

import com.deu.football_love.domain.Matches;
import com.deu.football_love.domain.Stadium;
import com.deu.football_love.domain.Team;
import com.deu.football_love.dto.CreateMatchResponse;
import com.deu.football_love.dto.MatchDto;
import com.deu.football_love.repository.MatchRepository;
import com.deu.football_love.repository.StadiumRepository;
import com.deu.football_love.repository.TeamRepository;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final StadiumRepository stadiumRepository;

    @Transactional(readOnly = true)
    public MatchDto findMatch(Long matchId)
    {
        Matches findMatch = matchRepository.selectMatch(matchId);
        if (findMatch == null)
            return null;
        return MatchDto.from(findMatch);
    }

    @Transactional
    public CreateMatchResponse addMatch(String teamName, Long stadiumId, LocalDateTime reservationTime)
    {
        Team findTeam = teamRepository.selectTeam(teamName);
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
