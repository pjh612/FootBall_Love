package com.deu.football_love.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationJoinTeam extends BaseEntity {

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

    public void delete()
    {
        team.getApplicationJoinTeams().remove(this);
        this.team = null;
        member.getApplicationJoinTeams().remove(this);
        this.member = null;
    }
}
