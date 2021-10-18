package com.deu.football_love.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue
    private Long id;

    @Column(name = "team_name")
    private String name;

    @OneToMany(mappedBy = "team")
    private List<TeamAdmin> teamAdmins = new ArrayList<>();

    @Column(name="team_createdate")
    private LocalDate createDate;


    @OneToMany(mappedBy = "match")
    private List<MatchTeam> matches = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>();
}
