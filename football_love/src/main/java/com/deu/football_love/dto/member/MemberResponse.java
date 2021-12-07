package com.deu.football_love.dto.member;

import com.deu.football_love.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberResponse {

	private Long number;

	private String id;

	private String nickname;

	private String name;

	private String email;

	public MemberResponse(Member member) {
		this.number = member.getNumber();
		this.id = member.getId();
		this.nickname = member.getNickname();
		this.name = member.getName();
		this.email = member.getEmail();
	}
}