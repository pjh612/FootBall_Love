package com.deu.football_love.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Matches extends com.deu.football_love.domain.BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "matches_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private com.deu.football_love.domain.Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id")
    private com.deu.football_love.domain.Stadium stadium;

    @Column(name = "matches_reservation_time")
    private LocalDateTime reservationTime;

    @Column(name = "matches_approval")
    private Boolean approval;
}
