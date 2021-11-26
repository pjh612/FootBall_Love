package com.deu.football_love.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseTimeDto{
    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
