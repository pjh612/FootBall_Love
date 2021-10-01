package com.deu.football_love.domain;

import javax.persistence.*;

@Entity
public class MatchTeam {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Matches match;

    @ManyToOne
    @JoinColumn(name = "team_A",referencedColumnName = "team_id")
    private Team teamA;

    @ManyToOne
    @JoinColumn(name = "team_B" ,referencedColumnName = "team_id")
    private Team teamB;
}
