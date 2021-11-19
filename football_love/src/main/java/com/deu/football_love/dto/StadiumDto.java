package com.deu.football_love.dto;

import com.deu.football_love.domain.Stadium;
import lombok.Getter;

@Getter
public class StadiumDto {
    private Long id;
    private String type;
    private String size;
    private Long cost;

    public StadiumDto(Long id, String type, String size, Long cost) {
        this.id = id;
        this.type = type;
        this.size = size;
        this.cost = cost;
    }

    public static StadiumDto from(Stadium stadium)
    {
        return new StadiumDto(stadium.getId(), stadium.getType(), stadium.getSize(), stadium.getCost());
    }
}
