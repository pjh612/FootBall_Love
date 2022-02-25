package com.deu.football_love.dto.member;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BusinessJoinResponse {
  @NotNull
  private Long memberNumber;
  @NotNull
  private Long companyId;
}
