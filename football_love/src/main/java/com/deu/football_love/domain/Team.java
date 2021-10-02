package com.deu.football_love.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue
    private Long id;

    @Column(name = "team_name")
    private String name;

    @Column(name="team_createdate")
    private LocalDate createDate;

    @ManyToMany(mappedBy = "teams")
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "match")
    private List<MatchTeam> matches = new ArrayList<>();
}
