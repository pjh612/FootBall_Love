package com.deu.football_love.domain;

import com.deu.football_love.domain.type.StadiumFieldType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stadium extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "stadium_id")
    private Long id;

    @Column(name = "stadium_type")
    @Enumerated(EnumType.STRING)
    private StadiumFieldType type;

    @Column(name = "stadium_size")
    private String size;

    @Column(name = "stadium_cost")
    private Long cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    @OneToMany(mappedBy = "stadium")
    private List<Matches> matches = new ArrayList<>();

    public Stadium(StadiumFieldType type, String size, Long cost, Company company) {
        this.type = type;
        this.size = size;
        this.cost = cost;
        this.company = company;
    }
}
