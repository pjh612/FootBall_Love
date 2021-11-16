package com.deu.football_love.dto;

import com.deu.football_love.domain.Matches;

import java.time.LocalDateTime;

public class CreateMatchResponse {
    private Long matchId;
    private String teamName;
    private Long stadiumId;
    private LocalDateTime reservation_time;


    public CreateMatchResponse(Long matchId, String teamName, Long stadiumId, LocalDateTime reservation_time) {
        this.matchId = matchId;
        this.teamName = teamName;
        this.stadiumId = stadiumId;
        this.reservation_time = reservation_time;
    }

    public static CreateMatchResponse from(Matches match)
    {
        return new CreateMatchResponse(match.getId(), match.getTeam().getName(), match.getStadium().getId(), match.getReservationTime());
    }
}
