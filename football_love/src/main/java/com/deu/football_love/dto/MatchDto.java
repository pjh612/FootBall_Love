package com.deu.football_love.dto;

import com.deu.football_love.domain.Matches;

import java.time.LocalDateTime;

public class MatchDto {
    private Long matchId;
    private String teamName;
    private Long stadiumId;
    private LocalDateTime reservation_time;

    public MatchDto(Long matchId, String teamName, Long stadiumId, LocalDateTime reservation_time) {
        this.matchId = matchId;
        this.teamName = teamName;
        this.stadiumId = stadiumId;
        this.reservation_time = reservation_time;
    }

    public static MatchDto from(Matches match)
    {
        return new MatchDto(match.getId(), match.getTeam().getName(), match.getStadium().getId(), match.getReservationTime());
    }
}
