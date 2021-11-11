package com.deu.football_love.dto;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.common.reflection.XMember;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
	private String id;

	private String pwd;

	private String nickname;

	private String name;

	private LocalDate birth;

	private Address address;

	private String email;

	private String phone;
	
	private List<Long> postId;
	
	private List<Long> adminsId;
	
	private List<Long> teamMembersId;
	
	private List<Long> participationMembersId;
	
	private Long withdrawalMemberId;
	
	public MemberDto(Member member) {
		this.id = member.getId();
		this.pwd = member.getPwd();
		this.nickname = member.getNickname();
		this.name = member.getName();
		this.birth = member.getBirth();
		this.address = member.getAddress();
		this.email = member.getEmail();
		this.phone = member.getPhone();
		this.withdrawalMemberId = member.getWithdrawalMember().getId();
		member.getPosts().forEach(post -> postId.add(post.getId()));
		member.getAdmins().forEach(admin -> adminsId.add(admin.getId()));
		member.getTeamMembers().forEach(teamMember -> postId.add(teamMember.getId()));
		member.getParticipationMembers().forEach(participation -> postId.add(participation.getId()));
	}
}
