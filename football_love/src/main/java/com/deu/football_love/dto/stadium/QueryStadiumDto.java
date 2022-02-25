package com.deu.football_love.dto.stadium;

import com.deu.football_love.domain.Stadium;
import com.deu.football_love.domain.type.StadiumFieldType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QueryStadiumDto {
    private Long id;
    private StadiumFieldType type;
    private String size;
    private Long cost;

    public QueryStadiumDto(Long id, StadiumFieldType type, String size, Long cost) {
        this.id = id;
        this.type = type;
        this.size = size;
        this.cost = cost;
    }

    public static QueryStadiumDto from(Stadium stadium)
    {
        return new QueryStadiumDto(stadium.getId(), stadium.getType(), stadium.getSize(), stadium.getCost());
    }
}
