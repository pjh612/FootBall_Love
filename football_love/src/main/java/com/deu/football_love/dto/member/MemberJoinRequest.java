package com.deu.football_love.dto.member;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "memberJoinRequestBuilder")
@Getter
@Setter
@ToString
public class MemberJoinRequest {
  @NotNull
  @Size(min = 1, max = 20)
  private String id;

  @NotNull
  @Size(min = 1, max = 150)
  private String pwd;

  @NotNull
  @Size(min = 1, max = 20)
  private String nickname;

  @NotNull
  @Size(min = 1, max = 20)
  private String name;

  @Past
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
  private LocalDate birth;

  @NotNull
  private Address address;

  @NotNull
  @Size(min = 1, max = 50)
  @Email
  private String email;

  @NotNull
  @Size(min = 1, max = 13)
  @Pattern(regexp = "^(01[0|1|6|7|8|9])-(\\d{3,4})-(\\d{4})$")
  private String phone;

  @NotNull
  private MemberType type;

  public static MemberJoinRequestBuilder builder() {
    return memberJoinRequestBuilder();
  }
}
