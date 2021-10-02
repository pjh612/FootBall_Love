package com.deu.football_love.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Matches {

    @Id
    @GeneratedValue
    @Column(name="match_id")
    private Long id;

    @Column(name= "match_createdate")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "teamA")
    private List<MatchTeam> teamAs = new ArrayList<>();

    @OneToMany(mappedBy = "teamB")
    private List<MatchTeam> teamBs = new ArrayList<>();

}
