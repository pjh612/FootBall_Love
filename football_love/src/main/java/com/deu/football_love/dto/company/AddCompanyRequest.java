package com.deu.football_love.dto.company;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.deu.football_love.domain.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddCompanyRequest {
	@NotNull
	@Size(min=1,max=36)
    private String companyName;
	
	@NotNull
	@Positive
    private Long ownerNumber;
	
	@NotNull
    private Address location;
	
	@NotNull
	@Size(min=1,max=13)
	@Pattern(regexp = "^(01[0|1|6|7|8|9])-(\\d{3,4})-(\\d{4})$")
    private String tel;
	
	@NotNull
	@Size(min=1,max=100)
    private String description;

    public AddCompanyRequest(String name, Long ownerNumber, Address location, String tel, String description) {
        this.companyName = name;
        this.ownerNumber = ownerNumber;
        this.location = location;
        this.tel = tel;
        this.description = description;
    }
}
