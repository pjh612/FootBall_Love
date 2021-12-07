package com.deu.football_love.dto.member;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class UpdateMemberRequest {

    private String id;

    private String pwd;

    private String nickname;

    private String name;

    private LocalDate birth;

    private Address address;

    private String email;

    private String phone;

    private MemberType type;

    public UpdateMemberRequest(String id, String pwd, String nickname, String name, LocalDate birth, Address address, String email, String phone, MemberType type) {
        this.id = id;
        this.pwd = pwd;
        this.nickname = nickname;
        this.name = name;
        this.birth = birth;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.type = type;
    }
}
