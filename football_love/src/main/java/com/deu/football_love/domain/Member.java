package com.deu.football_love.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.member.UpdateMemberRequest;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    @Column(name = "member_creatdate")
    private LocalDateTime createDate;

    @Column(name = "member_type")
    private MemberType memberType;

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


    public void updateMember(UpdateMemberRequest request) {
        pwd = request.getPwd();
        email = request.getEmail();
        name = request.getName();
        nickname = request.getNickname();
        birth = request.getBirth();
        address = request.getAddress();
        phone = request.getPhone();
    }
}
