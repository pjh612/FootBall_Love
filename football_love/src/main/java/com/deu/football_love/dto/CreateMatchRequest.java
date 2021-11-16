package com.deu.football_love.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateMatchRequest {
    private String teamName;
    private Long stadiumId;
    private LocalDateTime reservation_time;
}
