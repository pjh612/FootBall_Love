package com.deu.football_love.domain;

import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.member.UpdateMemberRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(mappedBy = "owner", orphanRemoval = true)
    private Company company;

    @OneToMany(mappedBy = "writer")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<PostLike> likes = new ArrayList<>();


    @Builder(builderClassName = "MemberBuilder",
            builderMethodName = "memberBuilder")
    public Member(Long number, String id, String pwd, String nickname, String name, LocalDate birth, Address address, String email, String phone, MemberType memberType) {
        this.number = number;
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
        name = request.getName();
        nickname = request.getNickname();
        birth = request.getBirth();
        address = request.getAddress();
        phone = request.getPhone();
    }

    public void updateProfileImgUri(String uri) {
        profileImgUri = uri;
    }
}
