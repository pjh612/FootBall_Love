package com.deu.football_love.dto.match;

import com.deu.football_love.domain.Matches;

import java.time.LocalDateTime;

public class QueryMatchDto {
    private Long matchId;
    private String teamName;
    private Long stadiumId;
    private LocalDateTime reservation_time;

    public QueryMatchDto(Long matchId, String teamName, Long stadiumId, LocalDateTime reservation_time) {
        this.matchId = matchId;
        this.teamName = teamName;
        this.stadiumId = stadiumId;
        this.reservation_time = reservation_time;
    }

    public static QueryMatchDto from(Matches match)
    {
        return new QueryMatchDto(match.getId(), match.getTeam().getName(), match.getStadium().getId(), match.getReservationTime());
    }
}
