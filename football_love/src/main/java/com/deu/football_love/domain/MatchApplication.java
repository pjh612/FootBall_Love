package com.deu.football_love.domain;

import javax.persistence.*;

@Entity
public class MatchApplication {

    @Id
    @GeneratedValue
    @Column(name = "match_application_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_name")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matches_id")
    private Matches matches;

    @Column(name = "applicaiton_approval")
    private Boolean approval;
}
