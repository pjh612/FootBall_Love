package com.deu.football_love.domain;

import com.deu.football_love.domain.type.TeamMemberType;
import lombok.AccessLevel;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TeamMember", uniqueConstraints = {@UniqueConstraint(
        name = "TEAM_MEMBER_UNIQUE", columnNames = {"team_id", "member_number"}
)})
public class TeamMember extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "team_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_number")
    private Member member;

    private TeamMemberType type;

    public TeamMember(Team team, Member member, TeamMemberType type) {
        this.team = team;
        this.member = member;
        this.type = type;
    }

    public void addTeamMember(Team team, Member member, TeamMemberType type) {
        this.team = team;
        this.member = member;
        this.authority = authority;
    }

    protected TeamMember() {

    }
}
