package com.deu.football_love.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name", unique = true)
    private String name;

    @Column(name = "team_introduce")
    private String introduce;

    @Column(name="team_profile_img_uri")
    private String profileImgUri;

    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Matches> matches = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<MatchApplication> matchApplications = new ArrayList<>();

    @OneToMany(mappedBy = "team" )
    private List<ParticipationMember> participationMembers = new ArrayList<>();

    @OneToMany(mappedBy = "team" )
    private List<ApplicationJoinTeam> applicationJoinTeams = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Board> boards = new ArrayList<>();
}
