package com.deu.football_love.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.member.UpdateMemberRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "member_number")
  private Long number;


  @Column(name = "member_id", length = 20, unique = true)
  private String id;

  @Column(name = "member_pwd", length = 150)
  private String pwd;

  @Column(name = "member_nickname", length = 20)
  private String nickname;

  @Column(name = "member_name", length = 20)
  private String name;

  @Column(name = "member_birth")
  private LocalDate birth;

  @Column(name = "member_address")
  @Embedded
  private Address address;

  @Column(name = "member_email", unique = true)
  private String email;

  @Column(name = "member_phone")
  private String phone;

  @Column(name = "member_type")
  @Enumerated(EnumType.STRING)
  private MemberType memberType;

  @Column(name = "member_profile_img_uri")
  private String profileImgUri;

  @OneToMany(mappedBy = "author")
  private List<Post> posts = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<TeamMember> teamMembers = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<ParticipationMember> participationMembers = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<ApplicationJoinTeam> applicationJoinTeams = new ArrayList<>();

  @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
  private WithdrawalMember withdrawalMember;

  @OneToOne(mappedBy = "owner")
  private Company company;

  @OneToMany(mappedBy = "writer")
  private List<Comment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<PostLike> likes = new ArrayList<>();


  @Builder(builderClassName = "MemberBuilder",
      builderMethodName = "memberBuilder")
  public Member(String id, String pwd, String nickname, String name, LocalDate birth, Address address, String email, String phone,
      MemberType memberType) {
    this.id = id;
    this.pwd = pwd;
    this.nickname = nickname;
    this.name = name;
    this.birth = birth;
    this.address = address;
    this.email = email;
    this.phone = phone;
    this.memberType = memberType;
  }


  public void updateMember(UpdateMemberRequest request) {
    pwd = request.getPwd();
    email = request.getEmail();
    nickname = request.getNickname();
    address = request.getAddress();
    phone = request.getPhone();
  }

  public void updateProfileImgUri(String uri) {
    profileImgUri = uri;
  }
}
