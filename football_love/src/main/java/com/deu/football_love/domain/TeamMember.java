package com.deu.football_love.domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Data
public class TeamMember {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    Team team;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;
}
