package com.deu.football_love.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDto extends  BaseTimeDto {

    private Long createdBy;

    private Long lastModifiedBy;
}
