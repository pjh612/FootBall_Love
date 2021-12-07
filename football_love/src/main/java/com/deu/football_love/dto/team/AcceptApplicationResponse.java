package com.deu.football_love.dto.team;


public class AcceptApplicationResponse {
    private Long teamId;
    private String memberId;

    public AcceptApplicationResponse(Long teamId, String memberId) {
        this.teamId = teamId;
        this.memberId = memberId;
    }
}
