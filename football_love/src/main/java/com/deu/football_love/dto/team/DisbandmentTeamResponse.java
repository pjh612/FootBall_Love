package com.deu.football_love.dto.team;

import lombok.Getter;

@Getter
public class DisbandmentTeamResponse {
    private Long teamId;

    public DisbandmentTeamResponse(Long teamId) {
        this.teamId = teamId;
    }
}
