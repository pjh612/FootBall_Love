package com.deu.football_love.dto.team;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinApplyRequest {
  @NotNull
  @Size(min = 1, max = 200)
  private String message;
}
