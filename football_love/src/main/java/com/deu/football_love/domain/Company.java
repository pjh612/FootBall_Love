package com.deu.football_love.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue
    @Column(name= "company_id")
    private Long id;

    @Column(name= "company_name" , length = 36)
    private String name;

    @Column(name= "company_location")
    private Address location;


    @Column(name="company_tel", length = 13)
    private String tel;

    @Column(name= "company_description")
    private String description;

    @OneToMany(mappedBy = "id")
    private List<Stadium> stadiums = new ArrayList<>();

    public Company(String name, Address location, String tel, String description) {
        this.name = name;
        this.location = location;
        this.tel = tel;
        this.description = description;
    }
}
