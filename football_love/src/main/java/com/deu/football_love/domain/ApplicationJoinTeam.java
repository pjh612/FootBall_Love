package com.deu.football_love.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
public class ApplicationJoinTeam {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_name")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String message;

    public ApplicationJoinTeam(Team team, Member member, String message) {
        this.team = team;
        this.member = member;
        this.message = message;
    }

    protected ApplicationJoinTeam() {

    }
}
