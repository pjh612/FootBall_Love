package com.deu.football_love.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ApplicationJoinTeam {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_number")
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
