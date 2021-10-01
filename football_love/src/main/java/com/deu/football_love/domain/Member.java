package com.deu.football_love.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Member {
	@Id
	@Column(name = "member_id", length = 20)
	private String id;

	@Column(name = "member_pwd", length = 30)
	private String pwd;
}
