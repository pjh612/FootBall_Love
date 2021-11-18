package com.deu.football_love.dto;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Company;

public class CompanyDto {
    private Long companyId;
    private String companyName;
    private Address location;
    private String tel;
    private String description;

    public CompanyDto(Long id, String name, Address location, String tel, String description) {
        this.companyId = id;
        this.companyName =name;
        this.location = location;
        this.tel = tel;
        this.description = description;
    }

    public static CompanyDto from(Company company)
    {
        return new CompanyDto(company.getId(), company.getName(), company.getLocation(),company.getTel(), company.getDescription());
    }
}
