package com.deu.football_love.dto.team;

import lombok.Getter;

@Getter
public class CreateTeamResponse {
    private Long teamId;

    public CreateTeamResponse(Long teamId) {
        this.teamId = teamId;
    }
}
