package com.deu.football_love.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
@Getter
public class Address {
	@NotNull
	@Size(min=1,max=20)
    private String city;
	
	@NotNull
	@Size(min=1,max=20)
    private String street;
	
	@NotNull
	@Size(min=1,max=20)
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
