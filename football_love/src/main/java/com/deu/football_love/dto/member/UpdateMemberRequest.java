package com.deu.football_love.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder(builderMethodName = "updateMemberRequestBuilder")
public class UpdateMemberRequest {
  @NotNull
  @Size(min = 1, max = 150)
  private String pwd;

  @NotNull
  @Size(min = 1, max = 20)
  private String nickname;

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

  public static UpdateMemberRequestBuilder builder() {
    return updateMemberRequestBuilder();
  }
}
