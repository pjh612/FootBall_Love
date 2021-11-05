package com.deu.football_love.domain;

import com.deu.football_love.domain.type.AuthorityType;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TeamMember", uniqueConstraints = {@UniqueConstraint(
        name = "TEAM_MEMBER_UNIQUE", columnNames = {"team_name", "member_id"}
)})
public class TeamMember {

    @Id
    @GeneratedValue
    @Column(name="team_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_name")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private AuthorityType authority;

    public TeamMember(Team team, Member member,AuthorityType authority) {
        this.team = team;
        this.member = member;
        this.authority = authority;
    }

    protected TeamMember() {

    }
}
