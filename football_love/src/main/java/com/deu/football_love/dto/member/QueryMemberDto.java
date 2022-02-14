package com.deu.football_love.dto.member;

import com.deu.football_love.domain.Company;
import com.deu.football_love.dto.BaseDto;
import java.time.LocalDate;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.BaseEntity;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.company.QueryCompanyDto;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryMemberDto extends BaseDto {

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

  private QueryCompanyDto company;


  public QueryMemberDto(Long number, String id, String nickname, String name, String email,
      LocalDate birth, Address address, String phone, MemberType type, String profileUri,
      Company company, Long createdBy, Long lastModifiedBy, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
    this.number =number;
    this.id = id;
    this.nickname = nickname;
    this.name = name;
    this.email = email;
    this.birth = birth;
    this.address = address;
    this.phone = phone;
    this.type = type;
    this.profileUri = profileUri;
    if (company != null) {
      this.company = QueryCompanyDto.from(company);
    }
    setCreatedBy(createdBy);
    setLastModifiedBy(lastModifiedBy);
    setCreatedDate(createdDate);
    setLastModifiedDate(lastModifiedDate);

  }

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
    if (member.getCompany() != null) {
      this.company = QueryCompanyDto.from(member.getCompany());
    }
    setCreatedBy(member.getCreatedBy());
    setLastModifiedBy(member.getLastModifiedBy());
    setCreatedDate(member.getCreatedDate());
    setLastModifiedDate(member.getLastModifiedDate());

  }

  public static QueryMemberDto from(Member member)
  {
    return new QueryMemberDto(member);
  }
}
