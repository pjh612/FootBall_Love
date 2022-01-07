package com.deu.football_love.dto.company;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Company;
import lombok.Getter;

@Getter
public class QueryCompanyDto {

    private Long companyId;
    private Long owner;
    private String companyName;
    private Address location;
    private String tel;
    private String description;

    public QueryCompanyDto(Long id, String name, Long owner, Address location, String tel, String description) {
        this.companyId = id;
        this.companyName = name;
        this.owner = owner;
        this.location = location;
        this.tel = tel;
        this.description = description;
    }

    public QueryCompanyDto(Company company) {
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.owner = company.getOwner().getNumber();
        this.location = company.getLocation();
        this.tel = company.getTel();
        this.description = company.getDescription();
    }
    public static QueryCompanyDto from(Company company)
    {
        return new QueryCompanyDto(company.getId(), company.getName(), company.getOwner().getNumber(), company.getLocation(),company.getTel(), company.getDescription());
    }
}
