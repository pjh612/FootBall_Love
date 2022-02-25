package com.deu.football_love.dto.match;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApproveMatchRequest {

  @Positive
  @NotNull
  private Long matchApplicationId;

  public ApproveMatchRequest(Long matchApplicationId) {
    this.matchApplicationId = matchApplicationId;
  }
}
