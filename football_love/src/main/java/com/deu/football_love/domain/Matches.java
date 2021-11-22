package com.deu.football_love.domain;

import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Matches {
    @Id
    @GeneratedValue
    @Column(name = "matches_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", referencedColumnName = "stadium_id")
    private Stadium stadium;

    @Column(name = "matches_reservation_time")
    private LocalDateTime reservationTime;

    @Column(name="matches_approval")
    private Boolean approval;
}
