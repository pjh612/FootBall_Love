package com.deu.football_love.dto;


public class AcceptApplicationResponse {
    private String teamName;
    private String memberId;

    public AcceptApplicationResponse(String teamName, String memberId) {
        this.teamName = teamName;
        this.memberId = memberId;
    }
}
