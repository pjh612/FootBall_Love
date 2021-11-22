package com.deu.football_love.dto;

import com.deu.football_love.domain.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse {
	private String id;

	private String nickname;

	private String name;

	private String email;

	public MemberResponse(Member member) {
		this.id = member.getId();
		this.nickname = member.getNickname();
		this.name = member.getName();
		this.email = member.getEmail();
	}
}
