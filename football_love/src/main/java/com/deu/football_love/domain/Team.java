package com.deu.football_love.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Team extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "team_id")
  private Long id;

  @Column(name = "team_name", unique = true)
  private String name;

  @Column(name = "team_introduce")
  private String introduce;

  @Column(name = "team_profile_img_uri")
  private String profileImgUri;

  @OneToMany(mappedBy = "team")
  private List<TeamMember> teamMembers = new ArrayList<>();

  @OneToMany(mappedBy = "teamA")
  private List<Matches> matchesA = new ArrayList<>();

  @OneToMany(mappedBy = "teamB")
  private List<Matches> matchesB = new ArrayList<>();

  @OneToMany(mappedBy = "team")
  private List<MatchApplication> matchApplications = new ArrayList<>();

  @OneToMany(mappedBy = "team")
  private List<ParticipationMember> participationMembers = new ArrayList<>();

  @OneToMany(mappedBy = "team")
  private List<ApplicationJoinTeam> applicationJoinTeams = new ArrayList<>();

  @OneToMany(mappedBy = "team")
  private List<TeamBoard> boards = new ArrayList<>();

  public void updateTeamProfile(String profileImgUri, String introduce) {
    this.profileImgUri = profileImgUri;
    this.introduce = introduce;
  }
}
