package com.deu.football_love.domain;

import javax.persistence.*;

@Entity
public class Stadium {

    @Id
    @GeneratedValue
    @Column(name = "statdium_id")
    private Long id;

    @Column(name="stadium_type")
    private String type;

    @Column(name="stadium_size")
    private String size;

    @Column(name="stadium_cost")
    private Long cost;

    @ManyToOne
    @JoinColumn(name="company_id", referencedColumnName="company_id")
    private Company company;
}
