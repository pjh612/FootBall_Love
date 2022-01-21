package com.deu.football_love.dto.member;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
public class UpdateMemberRequest {
	@NotNull
	@Size(min=1,max=20)
    private String id;

	@NotNull
	@Size(min=1,max=150)
    private String pwd;

	@NotNull
	@Size(min=1,max=20)
    private String nickname;

	@NotNull
	@Size(min=1,max=20)
    private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDate birth;

	@NotNull
    private Address address;

	@NotNull
	@Size(min=1,max=50)
	@Email
    private String email;

	@NotNull
	@Size(min=1,max=13)
	@Pattern(regexp = "^(01[0|1|6|7|8|9])-(\\d{3,4})-(\\d{4})$")
    private String phone;

	@NotNull
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
