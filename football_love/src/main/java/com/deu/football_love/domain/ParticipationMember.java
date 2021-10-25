package com.deu.football_love.domain;

import javax.persistence.*;

@Entity
public class ParticipationMember {


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
    @JoinColumn(name = "participation_member_id", referencedColumnName = "member_id")
    private Member member;
}
