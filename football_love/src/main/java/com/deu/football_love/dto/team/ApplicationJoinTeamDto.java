package com.deu.football_love.dto.team;

import com.deu.football_love.domain.ApplicationJoinTeam;
import lombok.Getter;

@Getter
public class ApplicationJoinTeamDto {
    private Long id;

    private String teamName;

    private String memberId;

    private String message;

    public ApplicationJoinTeamDto(ApplicationJoinTeam applicationJoinTeam) {
        this.id = applicationJoinTeam.getId();
        this.teamName = applicationJoinTeam.getTeam().getName();
        this.memberId = applicationJoinTeam.getMember().getId();
        this.message = applicationJoinTeam.getMessage();
    }
}
