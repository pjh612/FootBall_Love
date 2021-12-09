package com.deu.football_love.dto.stadium;

import com.deu.football_love.domain.Stadium;
import lombok.Getter;

@Getter
public class AddStadiumResponse {
    private Long id;
    private String type;
    private String size;
    private Long cost;

    public AddStadiumResponse(Long id, String type, String size, Long cost) {
        this.id = id;
        this.type = type;
        this.size = size;
        this.cost = cost;
    }

    public static AddStadiumResponse from(Stadium stadium)
    {
        return new AddStadiumResponse(stadium.getId(), stadium.getType(), stadium.getSize(), stadium.getCost());
    }
}
