package com.deu.football_love.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {

    @Id
    @GeneratedValue
    @Column(name= "company_id")
    private Long id;

    @Column(name= "company_name")
    private String name;

    @Column(name= "company_location")
    private Address location;

    @Column(name="company_tel")
    private String tel;

    @Column(name= "company_description")
    private String description;

    @OneToMany(mappedBy = "id")
    private List<Stadium> stadiums = new ArrayList<>();
}
