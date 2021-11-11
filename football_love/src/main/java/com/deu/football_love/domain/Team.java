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
    @Column(name = "team_name")
    private String name;

    @Column(name="team_createdate")
    private LocalDate createDate;

    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<TeamMember> teamMembers = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Matches> matches = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<MatchApplication> matchApplications = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<ParticipationMember> participationMembers = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<ApplicationJoinTeam> applicationJoinTeams = new ArrayList<>();
}
