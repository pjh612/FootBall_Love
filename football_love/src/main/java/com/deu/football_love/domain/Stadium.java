package com.deu.football_love.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Stadium {

    @Id
    @GeneratedValue
    @Column(name = "stadium_id")
    private Long id;

    @Column(name="stadium_type")
    private String type;

    @Column(name="stadium_size")
    private String size;

    @Column(name="stadium_cost")
    private Long cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id", referencedColumnName="company_id")
    private Company company;

    @OneToMany(mappedBy = "stadium")
    private List<Matches> matches = new ArrayList<>();
}
