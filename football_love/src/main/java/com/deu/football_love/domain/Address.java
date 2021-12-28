package com.deu.football_love.domain;

import lombok.Getter;
import lombok.ToString;
import javax.persistence.Embeddable;

@Embeddable
@ToString
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
