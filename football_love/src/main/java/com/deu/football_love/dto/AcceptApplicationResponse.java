package com.deu.football_love.dto;


public class AcceptApplicationResponse {
    private Long teamId;
    private String memberId;

    public AcceptApplicationResponse(Long teamId, String memberId) {
        this.teamId = teamId;
        this.memberId = memberId;
    }
}
