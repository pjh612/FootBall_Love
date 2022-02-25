package com.deu.football_love.domain;

import com.deu.football_love.domain.type.TeamMemberType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TeamMember", uniqueConstraints = {@UniqueConstraint(
        name = "TEAM_MEMBER_UNIQUE", columnNames = {"team_id", "member_number"}
)})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Enumerated(EnumType.STRING)
    private TeamMemberType type;

    public TeamMember(Team team, Member member, TeamMemberType type) {
        this.team = team;
        this.member = member;
        this.type = type;
    }

    public void addTeamMember(Team team, Member member, TeamMemberType type) {
        this.team = team;
        team.getTeamMembers().add(this);
        this.member = member;
        member.getTeamMembers().add(this);
        this.type = type;
    }

    public void deleteTeamMember() {
        team.getTeamMembers().remove(this);
        this.team = null;
        member.getTeamMembers().remove(this);
        this.member = null;
    }
}
