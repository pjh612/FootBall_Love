package com.deu.football_love.dto;

import java.time.LocalDate;

import com.deu.football_love.domain.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
	private String id;

	private String pwd;

	private String nickname;

	private String name;

	private LocalDate birth;

	private Address address;

	private String email;

	private String phone;

	public JoinRequest(String id, String pwd, String nickname, String name, LocalDate birth, Address address, String email, String phone) {
		this.id = id;
		this.pwd = pwd;
		this.nickname = nickname;
		this.name = name;
		this.birth = birth;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}
}
