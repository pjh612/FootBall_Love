package com.deu.football_love.domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Data
public class TeamMember {

    @Id
    @GeneratedValue
    @Column(name="team_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;
}
