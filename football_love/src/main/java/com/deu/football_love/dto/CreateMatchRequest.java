package com.deu.football_love.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateMatchRequest {
    private Long teamId;
    private Long stadiumId;
    private LocalDateTime reservation_time;
}
