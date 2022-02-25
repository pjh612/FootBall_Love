package com.deu.football_love.domain;


import com.deu.football_love.domain.type.MatchState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Matches extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "matches_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamA_id",referencedColumnName = "team_id")
    private Team teamA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamB_id", referencedColumnName = "team_id")
    private Team teamB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @Column(name = "matches_reservation_time")
    private LocalDateTime reservationTime;

    @Column(name = "matches_state")
    private MatchState state;

    @Column(name = "application_refuse_message")
    private String refuseMessage = "";

}
