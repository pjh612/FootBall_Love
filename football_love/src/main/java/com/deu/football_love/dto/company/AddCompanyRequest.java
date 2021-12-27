package com.deu.football_love.dto.company;

import com.deu.football_love.domain.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddCompanyRequest {
    private String companyName;
    private Long ownerNumber;
    private Address location;
    private String tel;
    private String description;

    public AddCompanyRequest(String name, Long ownerNumber, Address location, String tel, String description) {
        this.companyName = name;
        this.ownerNumber = ownerNumber;
        this.location = location;
        this.tel = tel;
        this.description = description;
    }
}
