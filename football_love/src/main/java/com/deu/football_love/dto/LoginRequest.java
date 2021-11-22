package com.deu.football_love.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
	private String id;

	private String pwd;
}
