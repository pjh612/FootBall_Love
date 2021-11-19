package com.deu.football_love.dto;

public class AddStadiumRequest {
    private Long companyId;
    private String type;
    private String size;
    private Long cost;

    public AddStadiumRequest(Long companyId, String type, String size, Long cost) {
        this.companyId = companyId;
        this.type = type;
        this.size = size;
        this.cost = cost;
    }
}
