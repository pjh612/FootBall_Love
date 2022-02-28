package com.deu.football_love.dto.member;

import com.deu.football_love.domain.Address;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "businessMemberJoinRequestBuilder")
@Getter
@Setter
@ToString
public class BusinessJoinRequest extends MemberJoinRequest {
  @NotNull
  @Size(min=1,max=36)
  private String companyName;

  @NotNull
  private Address location;

  @NotNull
  @Size(min=1,max=13)
  @Pattern(regexp = "^(01[0|1|6|7|8|9])-(\\d{3,4})-(\\d{4})$")
  private String tel;

  @NotNull
  @Size(min=1,max=100)
  private String description;
  
}
