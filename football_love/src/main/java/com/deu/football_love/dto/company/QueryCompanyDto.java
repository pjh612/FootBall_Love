package com.deu.football_love.dto.company;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Company;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "queryCompanyDtoBuilder")
public class QueryCompanyDto {

  private Long companyId;
  private Long owner;
  private String companyName;
  private Address location;
  private String tel;
  private String description;

  public QueryCompanyDto(Company company) {
    this.companyId = company.getId();
    this.companyName = company.getName();
    this.owner = company.getOwner().getNumber();
    this.location = company.getLocation();
    this.tel = company.getTel();
    this.description = company.getDescription();
  }

  public static QueryCompanyDto from(Company company) {
    return builder().companyId(company.getId()).companyName(company.getName())
        .owner(company.getOwner().getNumber()).location(company.getLocation()).tel(company.getTel())
        .description(company.getDescription()).build();
  }

  public static QueryCompanyDtoBuilder builder() {
    return queryCompanyDtoBuilder();
  }
}
