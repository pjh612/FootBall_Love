package com.deu.football_love.dto.member;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.BaseEntity;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.company.QueryCompanyDto;
import com.deu.football_love.dto.team.QueryTeamMemberDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class QueryMemberDto extends BaseEntity {

	private Long number;

	private String id;

	private String nickname;

	private String name;

	private String email;

	private LocalDate birth;

	private Address address;

	private String phone;

	private String profileUri;

	private MemberType type;

	private List<QueryTeamMemberDto> teams;

	private List<Long> participationMembers = new ArrayList<>();

	private QueryCompanyDto company;



	public QueryMemberDto(Member member) {
		this.number = member.getNumber();
		this.id = member.getId();
		this.nickname = member.getNickname();
		this.name = member.getName();
		this.email = member.getEmail();
		this.birth = member.getBirth();
		this.address = member.getAddress();
		this.phone = member.getPhone();
		this.type = member.getMemberType();
		this.profileUri = member.getProfileImgUri();
		/*this.teams = member.getTeamMembers().stream().map(tm -> new TeamDto(tm.getTeam().getId(), tm.getTeam().getName(), tm.getType())).collect(Collectors.toList());
		if (member.getCompany() != null)
		this.company = QueryCompanyDto.from(member.getCompany());*/
		setCreatedBy(member.getCreatedBy());
		setLastModifiedBy(member.getLastModifiedBy());
		setCreatedDate(member.getCreatedDate());
		setLastModifiedDate(member.getLastModifiedDate());

	}
}
