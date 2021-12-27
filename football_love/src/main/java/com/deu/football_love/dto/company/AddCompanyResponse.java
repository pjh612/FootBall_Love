package com.deu.football_love.dto.company;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Company;
import lombok.Getter;

@Getter
public class AddCompanyResponse {
    private Long companyId;
    private String companyName;
    private Long ownerNumber;
    private Address location;
    private String tel;
    private String description;

    public AddCompanyResponse(Long id, String name, Long ownerNumber, Address location, String tel, String description) {
        this.companyId = id;
        this.companyName = name;
        this.ownerNumber = ownerNumber;
        this.location = location;
        this.tel = tel;
        this.description = description;
    }

    public static AddCompanyResponse from(Company company) {
        return new AddCompanyResponse(company.getId(), company.getName(), company.getOwner().getNumber(), company.getLocation(), company.getTel(), company.getDescription());
    }
}
