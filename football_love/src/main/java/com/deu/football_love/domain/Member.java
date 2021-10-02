package com.deu.football_love.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
	@Id
	@Column(name = "member_id", length = 20)
	private String id;

	@Column(name = "member_pwd", length = 30)
	private String pwd;

	@Column(name="member_birth")
	private LocalDate birth;

	@Column(name= "member_address")
	@Embedded
	private Address address;

	@Column(name= "member_email")
	private String Email;

	@Column(name= "member_phone")
	private String phone;

	@Column(name= "member_type")
	private String type;

	@Column(name= "member_creatdate")
	private LocalDateTime createDate;

	@OneToMany(mappedBy = "author")
	private List<Post> posts = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "team_member", joinColumns = @JoinColumn(name = "member_id"),
			inverseJoinColumns = @JoinColumn(name = "team_id"))
	private List<Team> teams = new ArrayList<>();


}
