package com.deu.football_love.domain;

import javax.persistence.*;

import lombok.Getter;

@Entity
@Getter
public class ParticipationMember extends BaseEntity {


    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matches_id", referencedColumnName = "matches_id")
    private Matches match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_member_number", referencedColumnName = "member_number")
    private Member member;
}
